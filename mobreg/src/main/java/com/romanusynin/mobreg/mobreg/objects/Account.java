package com.romanusynin.mobreg.mobreg.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "accounts")
public class Account implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String numberPolicy;

    @DatabaseField
    private String dateBirth;

    @DatabaseField
    private boolean isSelected;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberPolicy() {
        return numberPolicy;
    }

    public void setNumberPolicy(String numberPolicy) {
        this.numberPolicy = numberPolicy;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
