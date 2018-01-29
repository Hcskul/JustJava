/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

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
        CheckBox statusWhippedCream = (CheckBox) findViewById(R.id.CheckBox_WhippedCream);
        boolean whippedCreamCheckBox = statusWhippedCream.isChecked();

        CheckBox statusChocolate = (CheckBox) findViewById(R.id.CheckBox_Chocolate);
        boolean ChocolateCheckBox = statusChocolate.isChecked();

        EditText name = (EditText) findViewById(R.id.nameInput);
        String insertedName = name.getText().toString();

        int price = calculatePrice(whippedCreamCheckBox,ChocolateCheckBox);
        String displayMessage = (createOrderSummary(insertedName,price,whippedCreamCheckBox,ChocolateCheckBox));

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSubject) + insertedName);
        intent.putExtra(Intent.EXTRA_TEXT, displayMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    /**
     * Calculates the price of the order.
     * @param haswhippedCream
     * @param haschocolate
     * @return total price
     */
    private int calculatePrice(boolean haswhippedCream, boolean haschocolate) {
        int price = 5;

        // add 1€ if the user wants whipped Cream
        if (haswhippedCream){
            price += 1;
        }

        // add 2€ if the user wants chocolate
        if (haschocolate){
            price += 2;
        }
        return quantity * price;

    }

    /**
     * Create summary of the order
     * @param name
     * @param price
     * @param hasWhippedCream whether or not the user wants whipped cream
     * @return Pricemessage
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        String message = getString(R.string.javaName) + name;
        message +=getString(R.string.javaWhippedCream) + hasWhippedCream;
        message +=getString(R.string.javaChocolate) + hasChocolate;
        message +=getString(R.string.javaQantity) + quantity;
        message +=getString(R.string.javaTotal) + price;
        message +=getString(R.string.javaThankYou);
        return message;
    }


    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this,R.string.toastTooMuch,Toast.LENGTH_SHORT).show();
        }
        else {
            quantity += +1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this,R.string.toastTooFew,Toast.LENGTH_SHORT).show();
        }
        else {
            quantity += -1;
            displayQuantity(quantity);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int test) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + test);
    }

}