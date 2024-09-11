package dojo.supermarket.model

import java.util.*

class ShoppingCart {

    private var items: LinkedList<ShoppingCartItem> = LinkedList()
    private var productQuantities: MutableMap<Product, Double> = HashMap()


    internal fun getItems(): List<ShoppingCartItem> {
        return items
    }

    fun productQuantities(): Map<Product, Double> {
        return productQuantities
    }

    fun addShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        items.push(shoppingCartItem)
        val product = shoppingCartItem.product
        if (productQuantities.containsKey(product)) {
            productQuantities[product] = productQuantities[product]!! + shoppingCartItem.quantity
        } else {
            productQuantities[product] = shoppingCartItem.quantity
        }
    }
}
