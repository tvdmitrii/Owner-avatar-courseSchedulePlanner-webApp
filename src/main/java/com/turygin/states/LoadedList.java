package com.turygin.states;

import java.util.ArrayList;
import java.util.List;

public class LoadedList<T> {
    private List<T> items = new ArrayList<>();
    private int selectedId = -1;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> loadedItems) {
        this.items = loadedItems;
    }

    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public boolean getHasItems() {
        return items != null && !items.isEmpty();
    }

    public boolean getHasSelected() {
        return selectedId != -1;
    }

    public T getSelected() {
        if (!getHasItems() || !getHasSelected()) {
            return null;
        }
        return items.get(selectedId);
    }

    public void updateSelected(T item) {
        if (!getHasItems() || !getHasSelected()) {
            return;
        }
        items.set(selectedId, item);
    }

    public void removeSelected() {
        if (!getHasItems() || !getHasSelected()) {
            return;
        }
        this.items.remove(selectedId);
        selectedId = -1;
    }

    public void resetSelected() {
        selectedId = -1;
    }
}
