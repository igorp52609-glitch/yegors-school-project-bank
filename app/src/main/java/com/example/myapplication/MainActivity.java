package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAmount;
    private TextView textViewBalance;
    private Button btnWithdraw, btnDeposit, btnTransfer, btnGetData;
    private ListView listViewTransactions;

    private acountData account;
    private acountData targetAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextAmount = findViewById(R.id.editTextAmount);
        textViewBalance = findViewById(R.id.textViewBalance);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnGetData = findViewById(R.id.btnGetData);
        listViewTransactions = findViewById(R.id.listViewTransactions);

        account = new acountData(1234, "f", 1000.0);
        targetAccount = new acountData(67809, "d", 500.0);
        updateUI();
        btnGetData.setOnClickListener(this);
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = editTextAmount.getText().toString();
                try {
                    double amount = Double.parseDouble(inputStr);
                    account.withdraw(amount);
                    account.addTransaction("take", amount, "success");
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    account.addTransaction("take", 0, "uncorrect sum");
                    Toast.makeText(MainActivity.this, "you have to erite sum", Toast.LENGTH_SHORT).show();
                } catch (InsufficientFundsException | InvalidAmountException e) {
                    account.addTransaction("take", parseSafe(inputStr), e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    editTextAmount.setText("");
                    updateUI();
                }
            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = editTextAmount.getText().toString();
                try {
                    double amount = Double.parseDouble(inputStr);
                    account.deposit(amount);
                    account.addTransaction("put in", amount, "success");
                    Toast.makeText(MainActivity.this, "succes", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    account.addTransaction("put in", 0, "uncorrect sum");
                    Toast.makeText(MainActivity.this, "write correct sum", Toast.LENGTH_SHORT).show();
                } catch (InvalidAmountException e) {
                    account.addTransaction("put in", parseSafe(inputStr), e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    editTextAmount.setText("");
                    updateUI();
                }
            }
        });

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputStr = editTextAmount.getText().toString();

                if (inputStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "נא להזין סכום", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double amount = Double.parseDouble(inputStr);

                    account.transfer(targetAccount, amount);

                    account.addTransaction("transfer", amount, "success");
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    account.addTransaction("transfer", 0, "uncorrect sum");
                    Toast.makeText(MainActivity.this, "write correct sum", Toast.LENGTH_SHORT).show();
                } catch (InsufficientFundsException | InvalidAmountException | IllegalArgumentException e) {
                    account.addTransaction("transfer", parseSafe(inputStr), e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    editTextAmount.setText("");
                    updateUI();
                }
            }
        });
    }

    private double parseSafe(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }

    private void updateUI() {
        textViewBalance.setText(" balance: " + account.getBalance());

        ArrayList<String> displayList = new ArrayList<>();
        for (Transaction t : account.getTransaction()) {
            displayList.add(t.getTransactionInfo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );
        listViewTransactions.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        Toast.makeText(this, account.getName() + ", " + account.getBalance() + ", " + account.getNum(),Toast.LENGTH_LONG).show();
    }
}