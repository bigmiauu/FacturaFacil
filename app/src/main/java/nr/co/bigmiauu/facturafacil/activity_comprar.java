package nr.co.bigmiauu.facturafacil;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import harmony.java.awt.Color;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

import harmony.java.awt.Color;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;



public class activity_comprar extends ActionBarActivity {


    private EditText nombre;
    private EditText rfc;
    private EditText calle;
    private EditText colonia;
    private EditText localidad;
    private EditText cp;
    private String fecha;

    private String totaldinero;
    private TextView descripcion;

    private String pc1;
    private String pc2;
    private String pc3;
    private String pc4;

    private String pp1;
    private String pp2;
    private String pp3;
    private String pp4;

    private int pt1;
    private int pt2;
    private int pt3;
    private int pt4;




    private String totalproductos;


    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        descripcion=(TextView)findViewById(R.id.textView8);
        nombre=(EditText)findViewById(R.id.editText);
        rfc=(EditText)findViewById(R.id.editText2);
        calle=(EditText)findViewById(R.id.editText3);
        colonia=(EditText)findViewById(R.id.editText4);
        localidad=(EditText)findViewById(R.id.editText5);
        cp=(EditText)findViewById(R.id.editText6);






        Bundle bundle = getIntent().getExtras();
        totaldinero = bundle.getString("totaldinero");
        totalproductos = bundle.getString("totalproductos");

        pc1 = bundle.getString("pc1");
        pc2 = bundle.getString("pc2");
        pc3 = bundle.getString("pc3");
        pc4 = bundle.getString("pc4");

        pp1 = bundle.getString("pp1");
        pp2 = bundle.getString("pp2");
        pp3 = bundle.getString("pp3");
        pp4 = bundle.getString("pp4");

        pt1 = Integer.valueOf(pc1)*Integer.valueOf(pp1);
        pt2 = Integer.valueOf(pc2)*Integer.valueOf(pp2);
        pt3 = Integer.valueOf(pc3)*Integer.valueOf(pp3);
        pt4 = Integer.valueOf(pc4)*Integer.valueOf(pp4);

        if(!pc1.isEmpty()){
            if(!pc1.contentEquals("0"))
            descripcion.setText("Cantidad de producto 1 = "+pc1);
        }
        if(!pc2.isEmpty()){
            if(!pc2.contentEquals("0"))
            descripcion.setText(descripcion.getText()+"\nCantidad de producto 2 = "+pc2);
        }
        if(!pc3.isEmpty()){
            if(!pc3.contentEquals("0"))
            descripcion.setText(descripcion.getText()+"\nCantidad de producto 3 = "+pc3);
        }
        if(!pc4.isEmpty()){
            if(!pc4.contentEquals("0"))
            descripcion.setText(descripcion.getText()+"\nCantidad de producto 4 = "+pc4);
        }

        descripcion.setText(descripcion.getText()+"\n\nProductos comprados: "+totalproductos+"\nSumando un total de: "+totaldinero);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_comprar, menu);
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

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    public void generarpdf(View view) {



        if (nombre.getText().equals("") || rfc.getText().equals("") || calle.getText().equals("") || colonia.getText().equals("") || localidad.getText().equals("") || cp.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Falta uno o mas datos por llenar", Toast.LENGTH_SHORT).show();
        } else {

            // Creamos el documento.
            Document documento = new Document();

            try {

                // Creamos el fichero con el nombre que deseemos.
                File f = crearFichero(NOMBRE_DOCUMENTO);

                // Creamos el flujo de datos de salida para el fichero donde
                // guardaremos el pdf.
                FileOutputStream ficheroPdf = new FileOutputStream(
                        f.getAbsolutePath());

                // Asociamos el flujo que acabamos de crear al documento.
                PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

                //definimos las fuentes
                Font font = FontFactory.getFont(FontFactory.HELVETICA, 20,
                        Font.BOLD, Color.BLUE);

                Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.RED);

                Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 18,
                        Font.BOLD, Color.BLACK);

                // Incluimos el píe de página y una cabecera


                HeaderFooter cabecera = new HeaderFooter(new Phrase(
                        "FACTURA FACIL S.A DE C.V", font), false);
                HeaderFooter pie = new HeaderFooter(new Phrase(
                        "                                                                    Pagina 1 de 1"), false);

                documento.setHeader(cabecera);
                documento.setFooter(pie);

                // Abrimos el documento.
                documento.open();

                // Insertamos la tabla con datos de la empresa.
                PdfPTable tabla = new PdfPTable(3);

                tabla.addCell("Domicilio Fiscal:\n" +
                        "Mariano Escobedo 700\n" +
                        "COL. Nueva Anzures Miguel Hidalgo\n" +
                        "Distrito Federal México, C.P.: 11590\n" +
                        "TEL.:01(55)5263-8888\n");

                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.logo1);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image imagen = Image.getInstance(stream.toByteArray());
                tabla.addCell(imagen);


                fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());





                tabla.addCell("Folio Fiscal\n3bca6a9a-5073-4e74-b2bd-444ffa960f6c\n" +
                        "N°de Serie del Cert. del SAT\n00001000000300171326\n" +
                        "Fecha y hora de certicación\n"+fecha+"\n11:53:11p.m.");

                documento.add(tabla);


                // Insertamos la tabla con datos del cliente.
                documento.add(new Paragraph("           Datos del cliente", font3));
                documento.add(new Paragraph(" "));
                PdfPTable tabla1 = new PdfPTable(1);

                tabla1.addCell("Nombre: " + nombre.getText() + "\n" +
                        "RFC: " + rfc.getText() + "\n" +
                        "Calle: " + calle.getText() + "\n" +
                        "Colonia: " + colonia.getText() + "\n" +
                        "Localidad: " + localidad.getText() + "\n" +
                        "C.P.: " + cp.getText());

                documento.add(tabla1);

                // Insertamos la tabla con los datos de la orden.
                documento.add(new Paragraph("           Datos del pedido", font3));
                documento.add(new Paragraph(" "));
                PdfPTable tabla2 = new PdfPTable(5);

                tabla2.addCell("Cantidad");
                tabla2.addCell("Descripcion");
                tabla2.addCell("Unidad Medida");
                tabla2.addCell("Precio Unitario");
                tabla2.addCell("Total");

                if (!pc1.contains("0")) {
                    tabla2.addCell("" + pc1);
                    tabla2.addCell("Producto1");
                    tabla2.addCell("No aplica");
                    tabla2.addCell("" + pp1);
                    tabla2.addCell("" + pt1);
                }

                if (!pc2.contains("0")) {
                    tabla2.addCell("" + pc2);
                    tabla2.addCell("Producto2");
                    tabla2.addCell("No aplica");
                    tabla2.addCell("" + pp2);
                    tabla2.addCell("" + pt2);
                }

                if (!pc3.contains("0")) {
                    tabla2.addCell("" + pc3);
                    tabla2.addCell("Producto3");
                    tabla2.addCell("No aplica");
                    tabla2.addCell("" + pp3);
                    tabla2.addCell("" + pt3);
                }

                if (!pc4.contains("0")) {
                    tabla2.addCell("" + pc4);
                    tabla2.addCell("Producto4");
                    tabla2.addCell("No aplica");
                    tabla2.addCell("" + pp4);
                    tabla2.addCell("" + pt4);
                }


                documento.add(tabla2);


                //Agregamos los totales y el iva
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("                                                                                                                      Sub total: " + (pt1 + pt2 + pt3 + pt4)));
                documento.add(new Paragraph("                                                                                                                      I.V.A: " + 0.16 * (pt1 + pt2 + pt3 + pt4)));
                documento.add(new Paragraph("                                                                                                                      Total: " + (0.16 * (pt1 + pt2 + pt3 + pt4) + (pt1 + pt2 + pt3 + pt4))));

                // Añadimos un título con la fuente por defecto.
                documento.add(new Paragraph(""));


                // Insertamos una imagen que se encuentra en los recursos de la
                // aplicación.
           /* Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            documento.add(imagen);
            */


                // Agregar marca de agua
                font = FontFactory.getFont(FontFactory.HELVETICA, 25, Font.NORMAL,
                        Color.GRAY);
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_CENTER, new Paragraph(
                                "factura facil", font), 297.5f, 421,
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);

            } catch (DocumentException e) {

                Log.e(ETIQUETA_ERROR, e.getMessage());

            } catch (IOException e) {

                Log.e(ETIQUETA_ERROR, e.getMessage());

            } finally {

                // Cerramos el documento.
                documento.close();
            }

        }
    }
}
