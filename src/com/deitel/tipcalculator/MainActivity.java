// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
package com.deitel.tipcalculator;

import java.text.NumberFormat; // for currency formatting
import java.lang.Math;

import android.app.Activity; // base class for activities
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity 
{
   // currency and percent formatters 
   private static final NumberFormat currencyFormat = 
      NumberFormat.getCurrencyInstance();
   private static final NumberFormat percentFormat = 
      NumberFormat.getPercentInstance();
   private static final NumberFormat numberFormat =
		   NumberFormat.getNumberInstance();

   private double purchasePrice = 0.0; // purchase price entered by the user
   private TextView amountDisplayTextViewPP; // shows formatted purchase price amount
   private TextView amountEditTextPP;
   private double downPayment = 0.0; //down payment entered by user
   private TextView amountDisplayTextViewDP; //shows down payment
   private double interestRate = 0.0; //interest rate entered by user
   private TextView amountDisplayTextViewIR; //shows interest rate
   
   private double customYear = 30.0; // initial custom year 
   private TextView tenYearTextView; // shows 10 year monthly payments
   private TextView twentyYearTextView; // shows 20 year monthly payments
   private TextView customYearTextView; // shows custom year monthly payments
   private TextView yearTextView03; //shows custom year 
 

   // called when the activity is first created
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState); // call superclass's version
      setContentView(R.layout.activity_main); // inflate the GUI

      // get references to the TextViews 
      // that MainActivity interacts with programmatically
      amountDisplayTextViewPP = 
         (TextView) findViewById(R.id.amountDisplayTextViewPP);
      amountDisplayTextViewDP = (TextView) findViewById(R.id.amountDisplayTextViewDP);
      amountDisplayTextViewIR = (TextView) findViewById(R.id.amountDisplayTextViewIR);
      tenYearTextView = (TextView) findViewById(R.id.tenYearTextView);
      twentyYearTextView = (TextView) findViewById(R.id.twentyYearTextView);
      customYearTextView = (TextView) findViewById(R.id.customYearTextView);
      yearTextView03 = (TextView) findViewById(R.id.yearTextView04);
      
            
      // update GUI based on billAmount and customPercent 
      amountDisplayTextViewPP.setText(
         currencyFormat.format(purchasePrice));
      amountDisplayTextViewDP.setText(
    	 currencyFormat.format(downPayment));
      amountDisplayTextViewIR.setText(
    	  percentFormat.format(interestRate));
      updateStandard(); // update the 10 year monthly payments  
      updateTwentyYearLoan(); //update 20 year monthly payments
      updateCustom(); // update the custom year monthly payments

      // set TextWatcher
      EditText amountEditTextPP = 
         (EditText) findViewById(R.id.amountEditTextPP);
      	amountEditTextPP.addTextChangedListener(amountEditTextWatcherPP);    
      EditText amountEditTextDP = 
    	  (EditText) findViewById(R.id.amountEditTextDP);
    	  amountEditTextDP.addTextChangedListener(amountEditTextWatcherDP);    	      
      EditText amountEditTextIR = 
    	  (EditText) findViewById(R.id.amountEditTextIR);
    	  amountEditTextIR.addTextChangedListener(amountEditTextWatcherIR);	      
      
      // set customYearSeekBar's OnSeekBarChangeListener
      SeekBar customYearSeekBar = 
         (SeekBar) findViewById(R.id.customYearSeekBar);
      customYearSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
   } // end method onCreate

   
// updates 10 year loan price TextView
   private void updateStandard() 
   {
	   //10 year price
	   double months = 120.0;
	   double monthlyInterestRate = interestRate / 12.0; //gives monthly interest rate
	   double mInterestRate = 1.0 + monthlyInterestRate;
	   mInterestRate = Math.pow(mInterestRate, months);
	   double priceAfterDP = purchasePrice - downPayment;	   
	   double paymentPerMonth = 
		  priceAfterDP * (mInterestRate * monthlyInterestRate) / (mInterestRate - 1);
      // display 10 year payments as currency
	   tenYearTextView.setText(currencyFormat.format(paymentPerMonth));

   } // end method updateStandard
   
   // updates 20 year loan price TextView
   private void updateTwentyYearLoan() 
   {
	   //20 year price
	   double months = 240.0;
	   double monthlyInterestRate = interestRate / 12.0; //gives monthly interest rate
	   double mInterestRate = 1.0 + monthlyInterestRate;
	   mInterestRate = Math.pow(mInterestRate, months);
	   double priceAfterDP = purchasePrice - downPayment;	   
	   double paymentPerMonth = 
		  priceAfterDP * (mInterestRate * monthlyInterestRate) / (mInterestRate - 1);
      // display 20 year payments as currency
	   twentyYearTextView.setText(currencyFormat.format(paymentPerMonth));
	   
   } // end method updateTwentryYearLoan
   

   // updates the custom monthly payments based on users desired years
   private void updateCustom() 
   {

      // display custom year
      yearTextView03.setText(numberFormat.format(customYear));
           
      // calculate the custom payment
	   double months = customYear * 12.0;
	   double monthlyInterestRate = interestRate / 12.0; //gives monthly interest rate
	   double mInterestRate = 1.0 + monthlyInterestRate;
	   mInterestRate = Math.pow(mInterestRate, months);
	   double priceAfterDP = purchasePrice - downPayment;	   
	   double paymentPerMonth = 
		  priceAfterDP * (mInterestRate * monthlyInterestRate) / (mInterestRate - 1);
      
      // displays the custom monthly payment
      customYearTextView.setText(currencyFormat.format(paymentPerMonth));
   } // end method updateCustom
   
   // called when the user changes the position of SeekBar
   private OnSeekBarChangeListener customSeekBarListener =  
      new OnSeekBarChangeListener() 
   {
      // update customPercent, then call updateCustom
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
         boolean fromUser) 
      {
         // sets customYear to position of the SeekBar's thumb
         customYear = progress;
         updateCustom(); // update the custom year TextViews
      } // end method onProgressChanged

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) 
      {
      } // end method onStartTrackingTouch

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) 
      {
      } // end method onStopTrackingTouch
   }; // end OnSeekBarChangeListener

   // event-handling object that responds to amountEditText's events
   private TextWatcher amountEditTextWatcherPP = new TextWatcher() 
   {
	// called when the user enters a number
	      @Override
	      public void onTextChanged(CharSequence s, int start, 
	         int before, int count) 
	      {          
	         // convert amountEditText's text to a double
	         try
	         {
	            purchasePrice = Double.parseDouble(s.toString()) / 100.0;
	         } // end try
	         catch (NumberFormatException e)
	         {
	            purchasePrice = 0.0; // default if an exception occurs
	         } // end catch 

	         // display currency formatted payment
	         amountDisplayTextViewPP.setText(currencyFormat.format(purchasePrice));
	         updateStandard(); // update 10 year monthly payments
	         updateTwentyYearLoan(); //update 20 year monthly payments
	         updateCustom(); // update the custom monthly payment  
	      } // end method onTextChanged

      @Override
      public void afterTextChanged(Editable s) 
      {
      } // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) 
      {
      } // end method beforeTextChanged
   }; // end amountEditTextWatcherPP
   
   // event-handling object that responds to amountEditText's events
   private TextWatcher amountEditTextWatcherDP = new TextWatcher() 
   {
      // called when the user enters a number
      @Override
      public void onTextChanged(CharSequence s, int start, 
         int before, int count) 
      {         
         // convert amountEditText's text to a double
         try
         {
            downPayment = Double.parseDouble(s.toString()) / 100.0;
         } // end try
         catch (NumberFormatException e)
         {
            downPayment = 0.0; // default if an exception occurs
         } // end catch 

         // display currency formatted payment amount
         amountDisplayTextViewDP.setText(currencyFormat.format(downPayment));
         updateStandard(); // update 10 year monthly payments
         updateTwentyYearLoan(); //update 20 year monthly payments
         updateCustom(); //updates custom monthly payment amount
      } // end method onTextChanged

      @Override
      public void afterTextChanged(Editable s) 
      {
      } // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) 
      {
      } // end method beforeTextChanged
   }; // end amountEditTextWatcherDP
   
   // event-handling object that responds to amountEditText's events
   private TextWatcher amountEditTextWatcherIR = new TextWatcher() 
   {
      // called when the user enters a number
      @Override
      public void onTextChanged(CharSequence s, int start, 
         int before, int count) 
      {         
         
         // convert amountEditText's text to a double
         try
         {
            interestRate = Double.parseDouble(s.toString()) /100;
         } // end try
         catch (NumberFormatException e)
         {
            interestRate = 0.0; // default if an exception occurs
         } // end catch 

         // display currency formatted payment amount
         amountDisplayTextViewIR.setText(percentFormat.format(interestRate));
         updateStandard(); // update 10 year monthly payments
         updateTwentyYearLoan(); //update 20 year monthly payments
         updateCustom(); // update the custom monthly payment 
      } // end method onTextChanged

      @Override
      public void afterTextChanged(Editable s) 
      {
      } // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) 
      {
      } // end method beforeTextChanged
   }; // end amountEditTextWatcherIR
} // end class MainActivity


/*************************************************************************
* (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
* Pearson Education, Inc. All Rights Reserved.                           *
*                                                                        *
* DISCLAIMER: The authors and publisher of this book have used their     *
* best efforts in preparing the book. These efforts include the          *
* development, research, and testing of the theories and programs        *
* to determine their effectiveness. The authors and publisher make       *
* no warranty of any kind, expressed or implied, with regard to these    *
* programs or to the documentation contained in these books. The authors *
* and publisher shall not be liable in any event for incidental or       *
* consequential damages in connection with, or arising out of, the       *
* furnishing, performance, or use of these programs.                     *
*************************************************************************/
