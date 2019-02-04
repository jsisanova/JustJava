package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editText = (EditText) findViewById(R.id.name_field);
        String name = editText.getText().toString();
//        Log.v("MainActivity", "Name = " + name);


        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
//        Log.v("MainActivity", "hasWhippedCream = " + hasWhippedCream);

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, hasWhippedCream, hasChocolate, price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order_for) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        displayMessage(priceMessage);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6,-122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
     * Calculates the total price of the order.
     * @param cream is the cup with whipped cream
     * @param choco is the cup with chocolate
     * @return total price
     */
    private int calculatePrice(boolean cream, boolean choco) {
//        basic price without toppings per 1 cup
        int initialPriceInclusiveToppings = 5;

        if (cream ) {
            initialPriceInclusiveToppings = initialPriceInclusiveToppings + 1;
        }
        if (choco) {
            initialPriceInclusiveToppings = initialPriceInclusiveToppings + 2;
        }
        return quantity * initialPriceInclusiveToppings;
    }

    /**
     * This method displays the given price + text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Takes in price of order and displays message
     *
     * @param inputName is the name of the customer
     * @param addWhippedCream if user wants whipped cream topping
     * @param addChocolate if user wants chocolate
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String inputName, boolean addWhippedCream, boolean addChocolate, int price) {
        return getString(R.string.order_summary_name, inputName) + "\n" + getString(R.string.order_summary_add_whipped_cream, addWhippedCream) + "\n" + getString(R.string.order_summary_add_chocolate, addChocolate) +"\n" + getString(R.string.order_summary_quantity, quantity) + "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price)) + "\n" + getString(R.string.thank_you);
    }


    //    This method is called when the plus button is clicked
    public void increment(View view) {
        if (quantity == 100) {
//            display toast message
            Toast.makeText(this, getString(R.string.more_than_100_coffees), Toast.LENGTH_SHORT).show();
//            exit the method early because there are max. 100 cups of coffee
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    //    This method is called when the minus button is clicked
    public void decrement(View view) {
        if (quantity == 1) {
//            display toast message
            Toast.makeText(this, getString(R.string.less_than_1_coffee), Toast.LENGTH_SHORT).show();
//            exit the method early because there is min. 1 cup of coffee
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}