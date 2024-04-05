package com.example.aninterface.Drawer.DietaryButtonRecyclerView;

public class ButtonItem {
    private String buttonText;
    private boolean isSelected;

    public ButtonItem(String buttonText) {
        this.buttonText = buttonText;
        this.isSelected = false; // Default not selected
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
