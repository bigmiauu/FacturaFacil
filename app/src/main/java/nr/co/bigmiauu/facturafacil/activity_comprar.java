package nr.co.bigmiauu.facturafacil;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;



import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import android.view.WindowManager;

import android.widget.EditText;




import android.os.Environment;

import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.IOException;


import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;


import java.util.Date;


import java.io.File;
import java.io.FileOutputStream;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import harmony.java.awt.Color;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;



import java.io.ByteArrayOutputStream;


import android.graphics.BitmapFactory;



import com.lowagie.text.Element;

import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;




public class activity_comprar extends ActionBarActivity {

    private String LOG_TAG = "GenerateQRCode";

    private EditText nombre;
    private EditText rfc;
    private EditText calle;
    private EditText colonia;
    private EditText localidad;
    private EditText cp;
    private String fecha = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());;

    private String totaldinero;
    public String totaltotal;
    private TextView descripcion;

    private String codigoqr;

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
    private final String NOMBRE_DOCUMENTO = "facturafacil"+fecha+".pdf";
    private final String NOMBRE_DOCUMENTO2 = "facturafacil"+fecha+".xml";

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

        totaltotal =""+(0.16 * (pt1 + pt2 + pt3 + pt4) + (pt1 + pt2 + pt3 + pt4));

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








    public Image imagen2;


    public void generarpdf(View view) {



        if (nombre.getText().toString().isEmpty() || rfc.getText().toString().isEmpty() || calle.getText().toString().isEmpty() || colonia.getText().toString().isEmpty() || localidad.getText().toString().isEmpty() || cp.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Falta uno o mas datos por llenar", Toast.LENGTH_SHORT).show();
        } else {


            // instanciamos el codigoqr
            fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            codigoqr = "facturafacil-"+fecha+"-"+nombre.getText()+"-"+rfc.getText()+"-"+localidad.getText()+"-Total:"+totaltotal;



                    //generamos el codigo qr
                    Log.v(LOG_TAG, codigoqr);

                    //creamos el tamaño
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    width= width* 1/2;
                    height= height* 1/2;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 1/2;

                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(codigoqr,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                    try {
                        Bitmap bitmapafl = qrCodeEncoder.encodeAsBitmap();
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        bitmapafl.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
                        try {
                            imagen2 = Image.getInstance(stream2.toByteArray());
                        } catch (BadElementException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

        }



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
                Font font = FontFactory.getFont(FontFactory.HELVETICA, 14,
                        Font.BOLD, Color.BLUE);

                Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 28,
                        Font.BOLD, Color.RED);

                Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 12,
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









                tabla.addCell("Folio Fiscal\n3bca6a9a-5073-4e74-b2bd-444ffa960f6c\n" +
                        "N°de Serie del Cert. del SAT\n00001000000300171326\n" +
                        "Fecha y hora de certicación\n"+fecha);

                documento.add(tabla);


                // Insertamos la tabla con datos del cliente.
                documento.add(new Paragraph("           Datos del cliente", font3));
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
                documento.add(new Paragraph("                                                                                                                      Total: " + totaltotal));

                documento.add(new Paragraph(" "));

                PdfPTable tabla3 = new PdfPTable(2);
                tabla3.addCell(imagen2);
                tabla3.addCell(codigoqr);
                documento.add(tabla3);

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


                //creamos el xml

                try {
                    File x = crearFichero(NOMBRE_DOCUMENTO2);
                    FileOutputStream ficheroxml = new FileOutputStream(
                            x.getAbsolutePath());

                    OutputStreamWriter fichxml = new OutputStreamWriter(ficheroxml);

                    fichxml.write("<cfdi:Comprobante xsi:schemaLocation=http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsdversion=\"3.2\" fecha=\""+fecha+"\"folio=\"1\" serie=\"A\" subTotal="+totaldinero+" descuento=\"0.00\" total="+totaltotal+" Moneda=\"MXN\"");
                    fichxml.write("\ncondicionesDePago=\"Contado\" tipoDeComprobante=\"ingreso\" noCertificado=\"20001000000100001703\"");
                    fichxml.write("\ncertificado=\"MIIFLTCCBBWgAwIBAgIUMjAwMDEwMDAwMDAxMDAwMDE3MDMwDQYJKoZIhvcNAQEFBQAwggFvMRgwFgYDVQQDDA9BLkMuIGRlIHBydWViYXMxLzAtBgNVBAoMJlNlcnZpY2lvIGRlIEFkbWluaXN0cmFjacOzbiBUcmlidXRhcmlhMTgwNgYDVQQLDC9BZG1pbmlzdHJhY2nDs24gZGUgU2VndXJpZGFkIGRlIGxhIEluZm9ybWFjacOzbjEpMCcGCSqGSIb3DQEJARYaYXNpc25ldEBwcnVlYmFzLnNhdC5nb2IubXgxJjAkBgNVBAkMHUF2LiBIaWRhbGdvIDc3LCBDb2wuIEd1ZXJyZXJvMQ4wDAYDVQQRDAUwNjMwMDELMAkGA1UEBhMCTVgxGTAXBgNVBAgMEERpc3RyaXRvIEZlZGVyYWwxEjAQBgNVBAcMCUNveW9hY8OhbjEVMBMGA1UELRMMU0FUOTcwNzAxTk4zMTIwMAYJKoZIhvcNAQkCDCNSZXNwb25zYWJsZTogSMOpY3RvciBPcm5lbGFzIEFyY2lnYTAeFw0xMDExMTkxOTM3MTRaFw0xMjExMTgxOTM3MTRaMIHGMSEwHwYDVQQDExhFRElUT1JJQUwgU0lTVEEgU0EgREUgQ1YxITAfBgNVBCkTGEVESVRPUklBTCBTSVNUQSBTQSBERSBDVjEhMB8GA1UEChMYRURJVE9SSUFMIFNJU1RBIFNBIERFIENWMSUwIwYDVQQtExxFU0k5MjA0Mjc4ODYgLyBIRUdUNzYxMDAzNFMyMR4wHAYDVQQFExUgLyBIRUdUNzYxMDAzTURGTlNSMDgxFDASBgNVBAsTC1N1Y3Vyc2FsQVZMMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD16plVVRcPvh0jIoj95adxvBJKf/HleXgPA/24EtvrpsxWvoK9F7xXnJac35QXAzl9ZGdR4LI0ftA4N7FjaGRYipfec2MspbZUWswlB0n5GMhP6OdRYzxtLJXbk97+zasMu0XrmLWqRDdSDnCqPKRsbD7yYAr4gf0FFLkUjJUFAQIDAQABo4HqMIHnMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMB0GA1UdDgQWBBQ8LZCEQvmi5pC+z/Z8sd+VW12bsjAuBgNVHR8EJzAlMCOgIaAfhh1odHRwOi8vcGtpLnNhdC5nb2IubXgvc2F0LmNybDAzBggrBgEFBQcBAQQnMCUwIwYIKwYBBQUHMAGGF2h0dHA6Ly9vY3NwLnNhdC5nb2IubXgvMB8GA1UdIwQYMBaAFOtZfQQimlONnnEaoFiWKfU54KDFMBAGA1UdIAQJMAcwBQYDKgMEMBMGA1UdJQQMMAoGCCsGAQUFBwMCMA0GCSqGSIb3DQEBBQUAA4IBAQCZRRU8wfK3GrV1GO05mrh59KtxfayjyOkwFipqeUq0/9faaX0YwTaph/gkZnYhV5/hLCurKOoKgCmpvGIy2/0416V1ClJt4SsAvju4UlvITaloVUDMqICAJpRKK/lCygmbuRPgi4noHrIpLlh8HTwyiWHq//C1Zl1hSTDrCZK48krdagtUml3IepYJSlFnps8Rr/7thF5DAkCwoScV5gsC7TRTy/rzTZh+MeXVDD+gJTR8wSD0njdkP4DfLSYyWVBu42YyXRLd9LWpgTX3lXh7uiVeiuLP5mFbZLmfrby0sSzlzG+Mz3ohhzdmz5keu3LexIphJ/Hzu1+MbUzi2xb9\" formaDePago=\"PAGO EN UNA SOLA EXHIBICIÓN\"");
                    fichxml.write("\nsello=\"WhU6wKjmAhfwehBHCbwVw2YANaFpjkJiV3rauEXllEMNpnaRnv4p8eBMAM1+3JxwVrFQK/+Gcy9FC3uTvOmF5HoMJGUui3ROW3wpleUCBbBdoJUVbWEVZs2UxgYewlpZ47A44O+Z9oHELFLmWdL0YEm8HF1WqoDBid8CF27tza8=\">");
                    fichxml.write("\n<cfdi:Emisor nombre=\"FACTURA FACIL S.A DE C.V\" rfc=\"ESI920427886\">");
                    fichxml.write("\n<cfdi:DomicilioFiscal calle=\"Mariano Escobedo\" noExterior=\"700\" colonia=\"Nueva Anzures\" localidad=\"Miguel Hidalgo\" estado=\"Distrito Federal México\" pais=\"México\" codigoPostal=\"11590\"/>");
                    fichxml.write("\n<cfdi:RegimenFiscal Regimen=\"Personal moral régimen general de ley.\"/>");
                    fichxml.write("\n\n<cfdi:Receptor nombre="+nombre.getText()+" rfc="+rfc.getText()+">");
                    fichxml.close();

                } catch (Exception ex) {

                }





                //Mostramos mensaje de confirmacion
                Toast.makeText(getApplicationContext(), "PDF y XML generados con exito y guardados en Descargas", Toast.LENGTH_SHORT).show();
            }

        }
    }

