package pl.zaliczenie.linijka2d.ui.photos;

import android.graphics.Bitmap;

public class Photo {
    private String title;
    private String patch;
    private Bitmap image;

    // Obiekt zdjęcia, potrzebny do pracy przy wyświetlaniu listy zapisanych zdjęć

    public Photo() {
        super();
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }


    public String getTitle() {
        return title;
    }

    public String getPatch() {
        return patch;
    }

    public Bitmap getImage() {
        return image;
    }


}