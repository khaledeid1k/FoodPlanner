package com.example.foodplanner.data.models.meal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Meal implements Parcelable {
     String idMeal;
    String strArea;
    String strCategory;
    String strMeal;
    String strTags;
    String strYoutube;
    String strMealThumb;
    String strIngredient1;
    String strIngredient10;
    String strIngredient11;
    String strIngredient12;
    String strIngredient13;
    String strIngredient14;
    String strIngredient15;
    String strIngredient16;
    String strIngredient17;
    String strIngredient18;
    String strIngredient19;
    String strIngredient2;
    String strIngredient20;
    String strIngredient3;
    String strIngredient4;
    String strIngredient5;
    String strIngredient6;
    String strIngredient7;
    String strIngredient8;
    String strIngredient9;
    String strInstructions;

    String strMeasure1;
    String strMeasure2;
    String strMeasure3;
    String strMeasure4;
    String strMeasure5;
    String strMeasure6;
    String strMeasure7;
    String strMeasure8;
    String strMeasure9;
    String strMeasure10;
    String strMeasure11;
    String strMeasure12;
    String strMeasure13;
    String strMeasure14;
    String strMeasure15;
    String strMeasure16;
    String strMeasure17;
    String strMeasure18;
    String strMeasure19;
    String strMeasure20;

    String strSource;

    protected Meal(Parcel in) {
        idMeal = in.readString();
        strArea = in.readString();
        strCategory = in.readString();
        strMeal = in.readString();
        strTags = in.readString();
        strYoutube = in.readString();
        strMealThumb = in.readString();
        strIngredient1 = in.readString();
        strIngredient10 = in.readString();
        strIngredient11 = in.readString();
        strIngredient12 = in.readString();
        strIngredient13 = in.readString();
        strIngredient14 = in.readString();
        strIngredient15 = in.readString();
        strIngredient16 = in.readString();
        strIngredient17 = in.readString();
        strIngredient18 = in.readString();
        strIngredient19 = in.readString();
        strIngredient2 = in.readString();
        strIngredient20 = in.readString();
        strIngredient3 = in.readString();
        strIngredient4 = in.readString();
        strIngredient5 = in.readString();
        strIngredient6 = in.readString();
        strIngredient7 = in.readString();
        strIngredient8 = in.readString();
        strIngredient9 = in.readString();
        strInstructions = in.readString();
        strMeasure1 = in.readString();
        strMeasure2 = in.readString();
        strMeasure3 = in.readString();
        strMeasure4 = in.readString();
        strMeasure5 = in.readString();
        strMeasure6 = in.readString();
        strMeasure7 = in.readString();
        strMeasure8 = in.readString();
        strMeasure9 = in.readString();
        strMeasure10 = in.readString();
        strMeasure11 = in.readString();
        strMeasure12 = in.readString();
        strMeasure13 = in.readString();
        strMeasure14 = in.readString();
        strMeasure15 = in.readString();
        strMeasure16 = in.readString();
        strMeasure17 = in.readString();
        strMeasure18 = in.readString();
        strMeasure19 = in.readString();
        strMeasure20 = in.readString();
        strSource = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrTags() {
        return strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public String getStrIngredient10() {
        return strIngredient10;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public String getStrIngredient15() {
        return strIngredient15;
    }

    public String getStrIngredient16() {
        return strIngredient16;
    }

    public String getStrIngredient17() {
        return strIngredient17;
    }

    public String getStrIngredient18() {
        return strIngredient18;
    }

    public String getStrIngredient19() {
        return strIngredient19;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public String getStrIngredient20() {
        return strIngredient20;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public String getStrIngredient5() {
        return strIngredient5;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMeasure1() {
        return strMeasure1;
    }

    public String getStrMeasure2() {
        return strMeasure2;
    }

    public String getStrMeasure3() {
        return strMeasure3;
    }

    public String getStrMeasure4() {
        return strMeasure4;
    }

    public String getStrMeasure5() {
        return strMeasure5;
    }

    public String getStrMeasure6() {
        return strMeasure6;
    }

    public String getStrMeasure7() {
        return strMeasure7;
    }

    public String getStrMeasure8() {
        return strMeasure8;
    }

    public String getStrMeasure9() {
        return strMeasure9;
    }

    public String getStrMeasure10() {
        return strMeasure10;
    }

    public String getStrMeasure11() {
        return strMeasure11;
    }

    public String getStrMeasure12() {
        return strMeasure12;
    }

    public String getStrMeasure13() {
        return strMeasure13;
    }

    public String getStrMeasure14() {
        return strMeasure14;
    }

    public String getStrMeasure15() {
        return strMeasure15;
    }

    public String getStrMeasure16() {
        return strMeasure16;
    }

    public String getStrMeasure17() {
        return strMeasure17;
    }

    public String getStrMeasure18() {
        return strMeasure18;
    }

    public String getStrMeasure19() {
        return strMeasure19;
    }

    public String getStrMeasure20() {
        return strMeasure20;
    }

    public String getStrSource() {
        return strSource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(idMeal);
        parcel.writeString(strArea);
        parcel.writeString(strCategory);
        parcel.writeString(strMeal);
        parcel.writeString(strTags);
        parcel.writeString(strYoutube);
        parcel.writeString(strMealThumb);
        parcel.writeString(strIngredient1);
        parcel.writeString(strIngredient10);
        parcel.writeString(strIngredient11);
        parcel.writeString(strIngredient12);
        parcel.writeString(strIngredient13);
        parcel.writeString(strIngredient14);
        parcel.writeString(strIngredient15);
        parcel.writeString(strIngredient16);
        parcel.writeString(strIngredient17);
        parcel.writeString(strIngredient18);
        parcel.writeString(strIngredient19);
        parcel.writeString(strIngredient2);
        parcel.writeString(strIngredient20);
        parcel.writeString(strIngredient3);
        parcel.writeString(strIngredient4);
        parcel.writeString(strIngredient5);
        parcel.writeString(strIngredient6);
        parcel.writeString(strIngredient7);
        parcel.writeString(strIngredient8);
        parcel.writeString(strIngredient9);
        parcel.writeString(strInstructions);
        parcel.writeString(strMeasure1);
        parcel.writeString(strMeasure2);
        parcel.writeString(strMeasure3);
        parcel.writeString(strMeasure4);
        parcel.writeString(strMeasure5);
        parcel.writeString(strMeasure6);
        parcel.writeString(strMeasure7);
        parcel.writeString(strMeasure8);
        parcel.writeString(strMeasure9);
        parcel.writeString(strMeasure10);
        parcel.writeString(strMeasure11);
        parcel.writeString(strMeasure12);
        parcel.writeString(strMeasure13);
        parcel.writeString(strMeasure14);
        parcel.writeString(strMeasure15);
        parcel.writeString(strMeasure16);
        parcel.writeString(strMeasure17);
        parcel.writeString(strMeasure18);
        parcel.writeString(strMeasure19);
        parcel.writeString(strMeasure20);
        parcel.writeString(strSource);
    }
}
