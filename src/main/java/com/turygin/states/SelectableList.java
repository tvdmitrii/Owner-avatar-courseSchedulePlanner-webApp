package com.turygin.states;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that represents a list that can remember a selected item in that list.
 * @param <T> the type of elements stored in the list
 */
public class SelectableList<T> {

    /** Underlying item list storage. */
    private List<T> items = new ArrayList<>();

    /** Selected element ID. */
    private int selectedId = -1;

    /**
     * Gets items.
     * @return the items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Sets items.
     * @param items the loaded items
     */
    public void setItems(List<T> items) {
        this.items = items;
        resetSelected();
    }

    /**
     * Gets selected list id.
     * @return the selected list id
     */
    public int getSelectedId() {
        return selectedId;
    }

    /**
     * Sets selected list id.
     * @param selectedId the selected list id
     */
    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    /**
     * Checks whether the list has any items.
     * @return true if there are items in the list, false otherwise
     */
    public boolean getHasItems() {
        return items != null && !items.isEmpty();
    }

    /**
     * Checks whether the list has a selected item.
     * @return true if there is a selected element, false otherwise
     */
    public boolean getHasSelected() {
        return selectedId != -1;
    }

    /**
     * Gets selected item. Verifies that there are elements in the list and a selected element.
     * @return the selected item
     */
    public T getSelected() {
        if (!getHasItems() || !getHasSelected()) {
            return null;
        }
        return items.get(selectedId);
    }

    /**
     * Update selected item in place.
     * @param item the item
     */
    public void updateSelected(T item) {
        if (!getHasItems() || !getHasSelected()) {
            return;
        }
        items.set(selectedId, item);
    }

    /**
     * Remove selected item form the list.
     */
    public void removeSelected() {
        if (!getHasItems() || !getHasSelected()) {
            return;
        }
        this.items.remove(selectedId);
        selectedId = -1;
    }

    /**
     * Reset selected.
     */
    public void resetSelected() {
        selectedId = -1;
    }

    public int getSize() {
        return items != null ? items.size() : 0;
    }
}
