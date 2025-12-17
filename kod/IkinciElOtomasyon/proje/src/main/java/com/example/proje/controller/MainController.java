package com.example.proje.controller;

import com.example.proje.model.*;
import com.example.proje.service.UrunYonetimi;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.example.proje.dao.FavoriteDAO;


public class MainController {

    // Ana sayfa
    @FXML private AnchorPane mainPage;
    // İlan Ver sayfası
    @FXML private AnchorPane ilanVerPage;

    // Üst kısım
    @FXML private TextField searchField;

    // Sol filtre alanları
    @FXML private ComboBox<String> categoryFilterComboBox;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> districtComboBox;
    @FXML private TextField minPriceField;
    @FXML private TextField maxPriceField;

    @FXML private RadioButton allRadioButton;
    @FXML private RadioButton last24HoursRadioButton;
    @FXML private RadioButton last3DaysRadioButton;
    @FXML private RadioButton last7DaysRadioButton;
    @FXML private RadioButton last15DaysRadioButton;

    private ToggleGroup dateToggleGroup;

    // GridPane
    @FXML private GridPane ilanlarGridMain;

    // İlan Ver sayfası bileşenleri
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField extraField;

    // Konum (il, ilçe) -> İlan Ver
    @FXML private ComboBox<String> ilanVerCityComboBox;
    @FXML private ComboBox<String> ilanVerDistrictComboBox;

    @FXML private ImageView photoPreview;

    private File selectedPhoto;
    private Urun selectedUrun;

    // Favoriler: DB’den yeniden çekilince referans değişmesin diye ID ile tutuyoruz
    private final Set<Integer> favoriteIds = new HashSet<>();

    // 🔥 FAVORİ DB DAO
    private FavoriteDAO favoriteDAO;

    // City-district mapping
    private final Map<String, List<String>> cityDistrictMap = new HashMap<>();

    private UrunYonetimi urunYonetimi;

    // DB’den çekilen ana listeyi cache’liyoruz (UI hızlı, DB az çağrı)
    private List<Urun> allCache = new ArrayList<>();
    private void loadFavoritesFromDb() {
        Task<List<Integer>> task = new Task<>() {
            @Override
            protected List<Integer> call() {
                return favoriteDAO.getAllFavoriteUrunIds();
            }
        };

        task.setOnSucceeded(e -> {
            List<Integer> ids = task.getValue();

            favoriteIds.clear();
            if (ids != null) {
                favoriteIds.addAll(ids);
            }

            System.out.println("Favoriler DB'den yüklendi: " + favoriteIds.size());

            // Ürünler ekrandaysa, kalpleri doğru renge çekmek için grid'i yenilemek iyi olur:
            refreshIlans(allCache);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            System.out.println("Favoriler DB'den yüklenemedi!");
        });

        new Thread(task, "db-load-favorites").start();
    }


    @FXML
    public void initialize() {
        urunYonetimi = (UrunYonetimi)
                com.example.proje.HelloApplication.ikinciElOtomasyon.getUrunYonetimi();

        favoriteDAO = new FavoriteDAO();

        // 🔥🔥🔥 FAVORİLERİ DB'DEN GERİ YÜKLE
        // Şehir-ilçe haritasını başlat
        cityDistrictMap.put("İstanbul", Arrays.asList("Kadıköy", "Beşiktaş", "Sancaktepe", "Maltepe", "Üsküdar", "Bakırköy", "Ataşehir", "Kartal", "Pendik", "Şişli"));
        cityDistrictMap.put("Ankara", Arrays.asList("Çankaya", "Kızılay", "Sincan", "Keçiören", "Mamak", "Altındağ", "Yenimahalle", "Etimesgut", "Gölbaşı", "Pursaklar"));
        cityDistrictMap.put("İzmir", Arrays.asList("Konak", "Karşıyaka", "Bornova", "Buca", "Balçova", "Menemen", "Bayraklı", "Narlıdere", "Çiğli", "Torbalı"));

        // UI bileşenlerini başlat
        categoryComboBox.getItems().addAll(
                "Elektronik", "Giyim", "Mobilya", "Kitap",
                "Spor", "Beyaz Eşya", "Müzik Aleti", "Sanat Eseri",
                "Ev Dekor", "Diğer"
        );
        categoryFilterComboBox.getItems().addAll(categoryComboBox.getItems());

        cityComboBox.getItems().addAll("İstanbul", "Ankara", "İzmir");
        districtComboBox.getItems().clear();

        ilanVerCityComboBox.getItems().addAll("İstanbul", "Ankara", "İzmir");
        ilanVerDistrictComboBox.getItems().clear();

        cityComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            districtComboBox.getItems().clear();
            if (newValue != null && cityDistrictMap.containsKey(newValue)) {
                districtComboBox.getItems().addAll(cityDistrictMap.get(newValue));
            }
        });

        ilanVerCityComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            ilanVerDistrictComboBox.getItems().clear();
            if (newValue != null && cityDistrictMap.containsKey(newValue)) {
                ilanVerDistrictComboBox.getItems().addAll(cityDistrictMap.get(newValue));
            }
        });

        // Tarih filtresi ToggleGroup
        dateToggleGroup = new ToggleGroup();
        allRadioButton.setToggleGroup(dateToggleGroup);
        last24HoursRadioButton.setToggleGroup(dateToggleGroup);
        last3DaysRadioButton.setToggleGroup(dateToggleGroup);
        last7DaysRadioButton.setToggleGroup(dateToggleGroup);
        last15DaysRadioButton.setToggleGroup(dateToggleGroup);
        allRadioButton.setSelected(true);

        loadFavoritesFromDb();

        // ✅ DB’den ürünleri UI’yi bloklamadan çek
        loadAllProductsAsync();
    }

    // =========================
    // ✅ ASYNC LOAD (DB)
    // =========================
    private void loadAllProductsAsync() {
        Task<List<Urun>> task = new Task<>() {
            @Override
            protected List<Urun> call() {
                return urunYonetimi.getUrunList(); // DB
            }
        };

        task.setOnSucceeded(e -> {
            allCache = task.getValue() != null ? task.getValue() : new ArrayList<>();
            System.out.println("ASYNC -> gelen ürün sayısı = " + allCache.size());

            refreshIlans(allCache);
        });


        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            showMessage("Hata", "Ürünler yüklenemedi (DB).", Alert.AlertType.ERROR);
        });

        new Thread(task, "db-load-products").start();
    }

    private void runDbActionAsync(Runnable action, Runnable onSuccessUi) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                action.run();
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            if (onSuccessUi != null) Platform.runLater(onSuccessUi);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            showMessage("Hata", "DB işlemi başarısız.", Alert.AlertType.ERROR);
        });

        new Thread(task, "db-action").start();
    }

    // "Mevcut Konumu Kullan" -> İstanbul / Maltepe
    @FXML
    private void useCurrentLocation() {
        cityComboBox.setValue("İstanbul");
        districtComboBox.setValue("Maltepe");
        showMessage("Bilgi", "Mevcut konum olarak İstanbul/Maltepe ayarlandı.", Alert.AlertType.INFORMATION);
    }

    // İlan Ver'e geç (Yeni ilan eklemek için)
    @FXML
    private void switchToIlanVerForAdd() {
        clearFields();
        idField.setDisable(false); // ✅ yeni eklemede id açık
        mainPage.setVisible(false);
        ilanVerPage.setVisible(true);
    }

    // İlan Ver'e geç (Düzenlemek için)
    private void switchToIlanVer() {
        mainPage.setVisible(false);
        ilanVerPage.setVisible(true);
    }

    // Geri dön (Ana sayfa)
    @FXML
    private void switchToMainPage() {
        ilanVerPage.setVisible(false);
        mainPage.setVisible(true);
        selectedUrun = null;
        // ✅ DB’den tekrar çek (güncel kalsın)
        loadAllProductsAsync();
    }

    // Fotoğraf Seç
    @FXML
    private void choosePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Fotoğraf Seç");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedPhoto = file;
            // ✅ background loading
            photoPreview.setImage(new Image(file.toURI().toString(), true));
        }
    }

    // "Ekle" butonu (İlan Ver sayfası)
    @FXML
    private void addUrun() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            String category = categoryComboBox.getValue();
            String extraInfo = extraField.getText();
            String konumIl = ilanVerCityComboBox.getValue();
            String konumIlce = ilanVerDistrictComboBox.getValue();

            if (name.isEmpty()) {
                showMessage("Hata", "Ürün adı boş olamaz!", Alert.AlertType.ERROR);
                return;
            }
            if (category == null || category.isEmpty()) {
                showMessage("Hata", "Kategori seçimi gerekli!", Alert.AlertType.ERROR);
                return;
            }
            if (konumIl == null || konumIl.isEmpty()) {
                showMessage("Hata", "İl seçimi gerekli!", Alert.AlertType.ERROR);
                return;
            }
            if (konumIlce == null || konumIlce.isEmpty()) {
                showMessage("Hata", "İlçe seçimi gerekli!", Alert.AlertType.ERROR);
                return;
            }

            String photoPath = (selectedPhoto != null) ? selectedPhoto.toURI().toString() : null;

            if (selectedUrun == null) {
                // ✅ Yeni ürün ekleme
                Urun urun = createUrunByCategory(category, id, name, price, extraInfo, photoPath, konumIl, konumIlce);

                runDbActionAsync(
                        () -> urunYonetimi.urunEkle(urun),
                        () -> {
                            showMessage("Başarılı", "Yeni ürün eklendi.", Alert.AlertType.INFORMATION);
                            clearFields();
                            switchToMainPage();
                        }
                );

            } else {
                // ✅ Güncelleme (ID değişmesin diye kilitledik)
                selectedUrun.setName(name);
                selectedUrun.setFiyat(price);
                selectedUrun.setKategori(category);
                selectedUrun.setExtraInfo(extraInfo);
                selectedUrun.setCity(konumIl);
                selectedUrun.setDistrict(konumIlce);
                if (selectedPhoto != null) {
                    selectedUrun.setPhotoPath(photoPath);
                }

                runDbActionAsync(
                        () -> urunYonetimi.urunGuncelle(selectedUrun),
                        () -> {
                            showMessage("Başarılı", "Ürün güncellendi.", Alert.AlertType.INFORMATION);
                            selectedUrun = null;
                            clearFields();
                            switchToMainPage();
                        }
                );
            }

        } catch (NumberFormatException e) {
            showMessage("Hata", "Lütfen geçerli bir ID ve fiyat girin!", Alert.AlertType.ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            showMessage("Hata", "Beklenmeyen hata oluştu.", Alert.AlertType.ERROR);
        }
    }

    private Urun createUrunByCategory(
            String category,
            int id,
            String name,
            double price,
            String extraInfo,
            String photoPath,
            String konumIl,
            String konumIlce
    ) {

        LocalDateTime now = LocalDateTime.now();
        int userId = 1; // ✅ TEK KULLANICI (admin)

        return switch (category) {

            case "Elektronik" -> new ElektronikUrun(
                    id, name, price,
                    "Marka" + id,
                    "Model" + id,
                    "Teknik Özellik",
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Giyim" -> new GiyimUrun(
                    id, name, price,
                    "L",
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Mobilya" -> new MobilyaUrun(
                    id, name, price,
                    "Ahşap",
                    "200x160x220 cm",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Kitap" -> new KitapUrun(
                    id, name, price,
                    "Yazar",
                    "YayınEvi",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Spor" -> new SporUrun(
                    id, name, price,
                    "MarkaSpor",
                    "ModelSpor",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Beyaz Eşya" -> new BeyazEsyaUrun(
                    id, name, price,
                    "MarkaBE",
                    "ModelBE",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Müzik Aleti" -> new MuzikAletiUrun(
                    id, name, price,
                    "MarkaMuzik",
                    "ModelMuzik",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Sanat Eseri" -> new SanatEseriUrun(
                    id, name, price,
                    "Sanatçı",
                    "Malzeme",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Ev Dekor" -> new EvDekorUrun(
                    id, name, price,
                    "Metal-Kristal",
                    "45x45x60 cm",
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            case "Diğer" -> new DigerUrun(
                    id, name, price,
                    extraInfo,
                    photoPath,
                    konumIl,
                    konumIlce,
                    now,
                    userId
            );

            default -> throw new IllegalArgumentException("Geçersiz kategori!");
        };
    }


    // Filtreleri uygula (✅ DB değil cache üzerinden)
    @FXML
    private void applyFilters() {
        String keyword = searchField.getText().trim().toLowerCase();

        List<Urun> filteredList = new ArrayList<>(allCache);

        if (!keyword.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        }

        String selectedCategory = categoryFilterComboBox.getValue();
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            String sc = selectedCategory.toLowerCase();
            filteredList = filteredList.stream()
                    .filter(u -> u.getKategori() != null && u.getKategori().toLowerCase().equals(sc))
                    .collect(Collectors.toList());
        }

        String selCity = cityComboBox.getValue();
        if (selCity != null && !selCity.isEmpty()) {
            String c = selCity.toLowerCase();
            filteredList = filteredList.stream()
                    .filter(u -> u.getCity() != null && u.getCity().toLowerCase().equals(c))
                    .collect(Collectors.toList());
        }

        String selDist = districtComboBox.getValue();
        if (selDist != null && !selDist.isEmpty()) {
            String d = selDist.toLowerCase();
            filteredList = filteredList.stream()
                    .filter(u -> u.getDistrict() != null && u.getDistrict().toLowerCase().equals(d))
                    .collect(Collectors.toList());
        }

        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;
        try {
            if (!minPriceField.getText().trim().isEmpty()) minPrice = Double.parseDouble(minPriceField.getText().trim());
            if (!maxPriceField.getText().trim().isEmpty()) maxPrice = Double.parseDouble(maxPriceField.getText().trim());
        } catch (NumberFormatException e) {
            showMessage("Hata", "Lütfen geçerli fiyat girin!", Alert.AlertType.ERROR);
            return;
        }

        double finalMin = minPrice;
        double finalMax = maxPrice;

        filteredList = filteredList.stream()
                .filter(u -> u.getFiyat() >= finalMin && u.getFiyat() <= finalMax)
                .collect(Collectors.toList());

        Toggle selectedToggle = dateToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            String txt = ((RadioButton) selectedToggle).getText();
            LocalDateTime now = LocalDateTime.now();

            filteredList = filteredList.stream().filter(urun -> {
                LocalDateTime t = urun.getIlanTarihi();
                if (t == null) return true;
                return switch (txt) {
                    case "Son 24 Saat" -> t.isAfter(now.minusHours(24));
                    case "Son 3 Gün" -> t.isAfter(now.minusDays(3));
                    case "Son 7 Gün" -> t.isAfter(now.minusDays(7));
                    case "Son 15 Gün" -> t.isAfter(now.minusDays(15));
                    default -> true;
                };
            }).collect(Collectors.toList());
        }

        refreshIlans(filteredList);
    }

    // Filtreleri temizle
    @FXML
    private void clearFilters() {
        searchField.clear();
        categoryFilterComboBox.getSelectionModel().clearSelection();
        cityComboBox.getSelectionModel().clearSelection();
        districtComboBox.getSelectionModel().clearSelection();
        minPriceField.clear();
        maxPriceField.clear();
        allRadioButton.setSelected(true);

        refreshIlans(allCache);
    }

    // Favoriler
    @FXML
    private void showFavorites() {
        List<Urun> favs = allCache.stream()
                .filter(u -> favoriteIds.contains(u.getId()))
                .collect(Collectors.toList());
        refreshIlans(favs);
    }

    // Tümü
    @FXML
    private void showAll() {
        refreshIlans(allCache);
    }

    // GridPane yenile
    private void refreshIlans(List<Urun> urunList) {
        Platform.runLater(() -> {
            ilanlarGridMain.getChildren().clear();

            int col = 0;
            int row = 0;
            for (Urun urun : urunList) {
                VBox box = createUrunBox(urun);
                ilanlarGridMain.add(box, col, row);

                col++;
                if (col > 4) {
                    col = 0;
                    row++;
                }
            }
        });
    }

    // Ürün kartı oluştur
    private VBox createUrunBox(Urun urun) {
        VBox urunBox = new VBox(10);
        urunBox.setStyle("-fx-background-color: #292929; -fx-padding: 10; -fx-border-color: #ffa500; -fx-border-width: 2;");
        urunBox.setAlignment(Pos.TOP_CENTER);
        urunBox.setPrefWidth(200);
        urunBox.setPrefHeight(350);

        ImageView ilanPhoto = new ImageView();
        ilanPhoto.setFitWidth(180);
        ilanPhoto.setFitHeight(180);
        ilanPhoto.setPreserveRatio(true);
        ilanPhoto.setSmooth(true);

        // ✅ Resmi UI’yi kilitlemeden yükle
        loadImageSafe(ilanPhoto, urun.getPhotoPath());

        VBox infoBox = new VBox(5);
        infoBox.setAlignment(Pos.TOP_LEFT);

        Label nameLabel = new Label("Ad: " + urun.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        nameLabel.setWrapText(true);

        Label priceLabel = new Label("Fiyat: " + String.format("%.2f", urun.getFiyat()) + " TL");
        priceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Label catLabel = new Label("Kategori: " + urun.getKategori());
        catLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12;");

        Label locationLabel = new Label("İl: " + urun.getCity() + ", İlçe: " + urun.getDistrict());
        locationLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12;");

        infoBox.getChildren().addAll(nameLabel, priceLabel, catLabel, locationLabel);

        Button delBtn = new Button("Sil");
        delBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        delBtn.setOnAction(e -> {
            int id = urun.getId();
            runDbActionAsync(
                    () -> {
                        favoriteDAO.removeFavorite(id); // önce favori
                        urunYonetimi.urunCikar(id);     // sonra ürün
                    },
                    () -> {
                        favoriteIds.remove(id);
                        loadAllProductsAsync();
                    }
            );
        });


        Button editBtn = new Button("Düzenle");
        editBtn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        editBtn.setOnAction(e -> {
            selectedUrun = urun;
            idField.setText(String.valueOf(urun.getId()));
            idField.setDisable(true); // ✅ update’te id değişmesin
            nameField.setText(urun.getName());
            priceField.setText(String.valueOf(urun.getFiyat()));
            categoryComboBox.setValue(urun.getKategori());
            extraField.setText(urun.getExtraInfo());
            ilanVerCityComboBox.setValue(urun.getCity());
            ilanVerDistrictComboBox.setValue(urun.getDistrict());

            if (urun.getPhotoPath() != null && !urun.getPhotoPath().isEmpty()) {
                loadImageSafe(photoPreview, urun.getPhotoPath());
            } else {
                photoPreview.setImage(null);
            }

            selectedPhoto = null;
            switchToIlanVer();
        });

        Button favBtn = new Button();
        updateFavButtonLook(favBtn, urun.getId());

        favBtn.setOnAction(e -> {
            int id = urun.getId();

            if (!favoriteIds.contains(id)) {
                // 1️⃣ RAM'e ekle
                favoriteIds.add(id);

                // 2️⃣ Database'e ekle (ASYNC)
                runDbActionAsync(
                        () -> favoriteDAO.addFavorite(id),
                        () -> {
                            // UI güncellemesi FX Thread'de
                            updateFavButtonLook(favBtn, id);
                            showMessage(
                                    "Bilgi",
                                    "Ürün favorilere eklendi.",
                                    Alert.AlertType.INFORMATION
                            );
                        }
                );

            } else {
                // 1️⃣ RAM'den çıkar
                favoriteIds.remove(id);

                // 2️⃣ Database'den sil (ASYNC)
                runDbActionAsync(
                        () -> favoriteDAO.removeFavorite(id),
                        () -> {
                            updateFavButtonLook(favBtn, id);
                            showMessage(
                                    "Bilgi",
                                    "Ürün favorilerden kaldırıldı.",
                                    Alert.AlertType.INFORMATION
                            );
                        }
                );
            }
        });



        HBox btnBox = new HBox(10, delBtn, editBtn, favBtn);
        btnBox.setAlignment(Pos.CENTER_LEFT);

        urunBox.getChildren().addAll(ilanPhoto, infoBox, btnBox);
        return urunBox;
    }

    private void updateFavButtonLook(Button favBtn, int urunId) {
        if (favoriteIds.contains(urunId)) {
            favBtn.setText("\u2665");
            favBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 16;");
        } else {
            favBtn.setText("\u2661");
            favBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16;");
        }
    }

    private void loadImageSafe(ImageView view, String path) {

        try {
            // ✅ 1) FotoPath varsa
            if (path != null && !path.isBlank()) {

                // classpath (/com/example/...) yoluysa
                if (path.startsWith("/")) {
                    URL url = getClass().getResource(path);
                    if (url != null) {
                        view.setImage(new Image(url.toExternalForm(), true));
                        return;
                    }
                }

                // file:/ veya http(s)
                if (path.startsWith("file:") || path.startsWith("http")) {
                    view.setImage(new Image(path, true));
                    return;
                }
            }

            // ❌ Buraya geldiysek: resim yok / hatalı
            // 👉 placeholder göster
            URL placeholder = getClass().getResource(
                    "/com/example/proje/images/no-image.jpg"
            );

            if (placeholder != null) {
                view.setImage(new Image(placeholder.toExternalForm(), true));
            } else {
                view.setImage(null); // çok ekstrem durum
            }

        } catch (Exception e) {
            view.setImage(null);
        }
    }



    private void clearFields() {
        idField.clear();
        idField.setDisable(false);
        nameField.clear();
        priceField.clear();
        extraField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        ilanVerCityComboBox.getSelectionModel().clearSelection();
        ilanVerDistrictComboBox.getSelectionModel().clearSelection();
        photoPreview.setImage(null);
        selectedPhoto = null;
        selectedUrun = null;
    }

    // ✅ showAndWait UI kilitlemesin diye show()
    private void showMessage(String title, String content, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.show();
        });
    }
}
