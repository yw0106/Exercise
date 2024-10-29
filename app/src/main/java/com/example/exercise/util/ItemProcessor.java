package com.example.exercise.util;

import androidx.annotation.NonNull;

import com.example.exercise.model.GroupedItem;
import com.example.exercise.model.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemProcessor {

    /**
     * Groups and sorts a list of objects by their listId
     * and alphabetically by their name.
     *
     * @param itemList the list of items to be grouped and sorted; must not be null
     * @return a list of GroupedItem objects
     * @see GroupedItem#compareTo(GroupedItem)
     */
    public List<GroupedItem> groupAndSortItems(@NonNull List<Item> itemList) {
        Map<Integer, List<Item>> groupedItems = groupItemsByListId(itemList);
        return createAndSortGroupedItems(groupedItems);
    }

    private Map<Integer, List<Item>> groupItemsByListId(@NonNull List<Item> itemList) {
        return itemList.stream()
                .filter(item -> isValidItem(item))  // Filter out invalid items
                .collect(Collectors.groupingBy(item -> item.getListId()));  // Group by listId
    }

    private List<GroupedItem> createAndSortGroupedItems(@NonNull Map<Integer, List<Item>> groupedItems) {
        return groupedItems
                .entrySet()
                .stream()
                .map(entry -> new GroupedItem(entry.getKey(), sortItems(entry.getValue())))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Sorts a list of objects alphabetically by their name
     *
     * @param items the list of items to be sorted; must not be null
     * @return a new list of items sorted alphabetically by their name
     * @see Item#compareTo(Item)
     */
    private List<Item> sortItems(@NonNull List<Item> items) {
        return items.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    // check if an item is valid
    private boolean isValidItem(Item item) {
        return item != null && item.getName() != null && !item.getName().isEmpty();
    }

}