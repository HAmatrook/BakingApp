package com.example.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeModel implements Parcelable {

    String name;
    int servings;
    String image;
    List<String> quantity = new ArrayList<>();
    List<String>  measure  = new ArrayList<>();
    List<String>  ingredient  = new ArrayList<>();
    List<String>  shortDescription  = new ArrayList<>();
    List<String>  description  = new ArrayList<>();
    List<String>     videoURL  = new ArrayList<>();
    List<String>     thumbnailURL  = new ArrayList<>();
    List<String>    fullIngredient = new ArrayList<>();

    public RecipeModel(){}

    protected RecipeModel(Parcel in) {
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        quantity= in.createStringArrayList();
        measure = in.createStringArrayList();
        ingredient = in.createStringArrayList();
        shortDescription = in.createStringArrayList();
        description = in.createStringArrayList();
        videoURL = in.createStringArrayList();
        thumbnailURL = in.createStringArrayList();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<String> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<String> quantity) {
        this.quantity = quantity;
    }

    public List<String> getMeasure() {
        return measure;
    }

    public void setMeasure(List<String> measure) {
        this.measure = measure;
    }

    public List<String> getIngredient() {
        return ingredient;
    }


    public void setIngredient(List<String> ingredient) {
        this.ingredient = ingredient;
    }

    public List<String> getShortDescription() {
        return shortDescription;
    }


    public void addShortDescription(String shortDescription) {
        this.shortDescription.add(shortDescription);
    }

    public List<String> getDescription() {
        return description;
    }

    public void addDescription(String description) {
        this.description.add(description);
    }

    public List<String> getVideoURL() {
        return videoURL;
    }

    public void addVideoURL(String  videoURL) {
        this.videoURL.add(videoURL);
    }

    public void addThumbnailURL(String thumbnailURL) {
        this.thumbnailURL.add(thumbnailURL);
    }

    public List<String> getFullIngredient(){

        for(int i=0; i<ingredient.size(); i++) {
            fullIngredient.add( quantity.get(i)+" "+
                    measure.get(i)+ "  " + ingredient.get(i));
        }
        return  fullIngredient;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
        parcel.writeStringList(quantity);
        parcel.writeStringList(measure);
        parcel.writeStringList(ingredient);
        parcel.writeStringList(shortDescription);
        parcel.writeStringList(description);
        parcel.writeStringList(videoURL);
        parcel.writeStringList(thumbnailURL);
    }
}
