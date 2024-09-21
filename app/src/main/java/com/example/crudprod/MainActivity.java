package com.example.crudprod;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editPrice, editQuantity, editId;
    Button btnAdd, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editTextName);
        editPrice = findViewById(R.id.editTextPrice);
        editQuantity = findViewById(R.id.editTextQuantity);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewAll = findViewById(R.id.btnViewAll);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Funções CRUD
        addProduct();
        viewAllProducts();
        updateProduct();
        deleteProduct();
    }

    // Função para adicionar produto
    public void addProduct() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(
                        editName.getText().toString(),
                        Double.parseDouble(editPrice.getText().toString()),
                        Integer.parseInt(editQuantity.getText().toString())
                );

                if (isInserted)
                    Toast.makeText(MainActivity.this, "Produto Adicionado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Erro ao Adicionar Produto", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Função para visualizar todos os produtos
    public void viewAllProducts() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Nenhum Produto Encontrado", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Nome: ").append(res.getString(1)).append("\n");
                    buffer.append("Preço: ").append(res.getString(2)).append("\n");
                    buffer.append("Quantidade: ").append(res.getString(3)).append("\n\n");
                }

                // Exibir os dados
                Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Função para atualizar um produto
    public void updateProduct() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(
                        editId.getText().toString(),
                        editName.getText().toString(),
                        Double.parseDouble(editPrice.getText().toString()),
                        Integer.parseInt(editQuantity.getText().toString())
                );

                if (isUpdated)
                    Toast.makeText(MainActivity.this, "Produto Atualizado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Erro ao Atualizar Produto", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Função para deletar um produto
    public void deleteProduct() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Produto Deletado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Erro ao Deletar Produto", Toast.LENGTH_LONG).show();
            }
        });
    }
}