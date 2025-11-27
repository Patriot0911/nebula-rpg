package org.dev.nebula.core.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.EventListener;
import org.dev.nebula.core.eventBus.ItemBusEventBase;
import org.dev.nebula.core.services.UserService;

public abstract class ItemBase {
    private EventBus eventBus;
    protected UserService userService;

    public ItemBase(EventBus bus, UserService userService) {
        this.eventBus = bus;
        this.userService = userService;
    }

    public abstract ItemStack createItemStack();

    public abstract String getItemName();
    public abstract String getItemDescription();

    public abstract String getItemKeyName();
    public abstract NamespacedKey getItemTag();


    @SuppressWarnings("rawtypes")
    public <T extends ItemBusEventBase> void subscribeSkillEvent(Class<T> eventClass, EventListener<T> listener, String skillName) {
        eventBus.subscribe(eventClass,
            event -> {
                ItemMeta meta = event.getItem().getItemMeta();
                if(
                    !meta.getPersistentDataContainer().has(
                        getItemTag(),
                        PersistentDataType.BYTE
                    )
                ) return;
                listener.handle(event);
            }
        );
    };

    // public void onInteract(PlayerInteractEvent event) {}
    // public void onDrop(PlayerDropItemEvent event) {}
    // public void onPickup(EntityPickupItemEvent event) {}
    // public void onConsume(PlayerItemConsumeEvent event) {}
    // public void onInventoryClick(InventoryClickEvent event) {}
    // public void onCraft(CraftItemEvent event) {}
}
