/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
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

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText editTextValue = (EditText) findViewById(R.id.customer_name);
        String customerName = editTextValue.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);
//        displayMessage(priceMessage);
        String subject = "JustJava order for " + customerName;
        composeEmail(subject, priceMessage);




    }

    /**
     * Compose email message
     * @param subject of message
     * @param body is the order summary message
     */

    public void composeEmail(String subject, String body){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }


    }

    /**
     *  calculates price of the order
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate)
    {

        int price = 5;
        if(hasWhippedCream)
        {
            price += 1;
        }
        if (hasChocolate)
        {
            price += 2;
        }
        return price * quantity;
    }

    /**
     * createOrderSummary method
     * @param price of the order
     * @param hasWhippedCream if whipped cream is selected
     * @param hasChocolate if chocoloate is selected
     * @param customerName the users name from the edittext view
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String customerName)
    {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd Whipped Cream? " + hasWhippedCream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal = $" + price;
        priceMessage += " \n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     *
     * This method increments the quantity when the plus sign is clicked
     */

    public void increment(View view)
    {
        if (quantity >= 100){
            Toast.makeText(this, R.string.toast_more_than, Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }



    /**
     * This method decrements the quantity when the minus sign is clicked
     */

    public void decrement(View view)
    {
        if (quantity < 1){

            Toast.makeText(this, R.string.toast_less_than, Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//
//    private void displayMessage(String message)
//    {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}