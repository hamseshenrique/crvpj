package com.software.hms.projeto;

/**
 * Created by root on 25/07/16.
 */
public class ItemObjects {

    private String numMenu;
    private String menuName;
    private Integer menuImage;

    public ItemObjects(final String numMenu,final String menuName,final Integer menuImage){
        this.setNumMenu(numMenu);
        this.menuImage = menuImage;
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(Integer menuImage) {
        this.menuImage = menuImage;
    }

    public String getNumMenu() {
        return numMenu;
    }

    public void setNumMenu(String numMenu) {
        this.numMenu = numMenu;
    }
}
