package com.wbteam.YYzhiyue.view.city;


/**
 * Created by YoKey on 16/10/7.
 */
public class CityEntity implements Comparable<CityEntity> {
    private String name;
    private String id;
    private String pinyin;
    private char firstChar;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
        String first = pinyin.substring(0, 1);
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
           firstChar = 'A';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getFirstChar() {
        return firstChar;
    }

    @Override
    public int compareTo(CityEntity another) {
        return this.pinyin.compareTo(another.getPinyin());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CityEntity) {
            return this.id == ((CityEntity) o).getId();
        } else {
            return super.equals(o);
        }
    }
}
