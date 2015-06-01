package nr.co.bigmiauu.facturafacil;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Inicio extends ActionBarActivity {

    private TextView productos;
    private TextView total;

    public int pp1 = 150;
    public int pp2 = 50;
    public int pp3 = 250;
    public int pp4 = 300;
    public int totdinero = 0; //dinero total

    public int pc1 = 0;
    public int pc2 = 0;
    public int pc3 = 0;
    public int pc4 = 0;
    public int totalproductos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        productos=(TextView)findViewById(R.id.textView3);
        total=(TextView)findViewById(R.id.textView5);
        registrarEventos();
    }


    private void registrarEventos(){

        /// selecciona la lista en pantalla segun su ID
        final ListView lista1 = (ListView) findViewById(R.id.listView);

        // registra una accion para el evento click
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {


                /// Obtiene el valor de la casilla elegida
                String itemSeleccionado = adapterView.getItemAtPosition(i).toString();
                int posicion = i;
                if(totalproductos<=5) {
                    // muestra un mensaje
                    //Toast.makeText(getApplicationContext(),"Agregado "+itemSeleccionado, Toast.LENGTH_SHORT).show();
                    productos.setText(productos.getText() + "\n" + itemSeleccionado);

                    if (posicion == 0) {
                        totdinero = totdinero + pp1;
                        total.setText("" + totdinero);
                        pc1 = pc1 + 1;
                    }
                    if (posicion == 1) {
                        totdinero = totdinero + pp2;
                        total.setText("" + totdinero);
                        pc2 = pc2 + 1;
                    }
                    if (posicion == 2) {
                        totdinero = totdinero + pp3;
                        total.setText("" + totdinero);
                        pc3 = pc3 + 1;
                    }
                    if (posicion == 3) {
                        totdinero = totdinero + pp4;
                        total.setText("" + totdinero);
                        pc4 = pc4 + 1;
                    }
                    totalproductos++;

                }
                else{
                    Toast.makeText(getApplicationContext(),"Maximo de productos alcanzado", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void compra (View view){
        if(totdinero==0){
            Toast.makeText(getApplicationContext(),"No se ha seleccionado ningun producto", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent j = new Intent(this, activity_comprar.class);
            j.putExtra("totaldinero", "" + totdinero);
            j.putExtra("totalproductos", "" + totalproductos);
            j.putExtra("pc1", "" + pc1);
            j.putExtra("pc2", "" + pc2);
            j.putExtra("pc3", "" + pc3);
            j.putExtra("pc4", "" + pc4);

            j.putExtra("pp1", "" + pp1);
            j.putExtra("pp2", "" + pp2);
            j.putExtra("pp3", "" + pp3);
            j.putExtra("pp4", "" + pp4);
            startActivity(j);
        }
    }

    public void borrar (View view){
        totalproductos = 0;
        totdinero = 0;
        total.setText("0");
        productos.setText("");
        pc1=0;
        pc2=0;
        pc3=0;
        pc4=0;

    }
}
