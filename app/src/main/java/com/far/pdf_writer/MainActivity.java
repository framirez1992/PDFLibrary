package com.far.pdf_writer;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.far.farpdf.Entities.AmountsResume;
import com.far.farpdf.Entities.Client;
import com.far.farpdf.Entities.Header;
import com.far.farpdf.Entities.Invoice;
import com.far.farpdf.Entities.Order;
import com.far.farpdf.Entities.OrderDetail;
import com.far.farpdf.Entities.Payment;
import com.far.farpdf.Objects.Image;
import com.far.farpdf.Objects.Line;
import com.far.farpdf.Objects.LineItem;
import com.far.farpdf.Objects.Table;
import com.far.farpdf.Objects.TableCell;
import com.far.farpdf.Objects.TableColumn;
import com.far.farpdf.PDF.Writer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    String file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Writer w = new Writer(MainActivity.this);
                    file = w.createPDF("PDF", "test", format1());
                    w.promptForNextAction(file);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Writer w = new Writer(MainActivity.this);
                    file = w.createPDF("PDF", "test", format2());
                    w.promptForNextAction(file);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayList<Object> format1(){
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(new Image(BitmapFactory.decodeResource(getResources(), R.drawable.optica)).center());
        obj.add(new LineItem("Optica de Palma").bold().center());
        obj.add(new LineItem("Calle la Gloria #14/ El almirante").bold().center());
        obj.add(new LineItem("809-965-7845").bold().center());
        obj.add(new LineItem(" "));
        obj.add(new LineItem("Fecha: "+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").format(new Date())));
        obj.add(new LineItem("No: 052217"));
        obj.add(new LineItem(" "));
        obj.add(new LineItem("Vendedor: 005 - Javier Polanco"));
        obj.add(new LineItem("Cliente:  Jose Montes"));
        obj.add(new LineItem(" "));
        obj.add(new LineItem("FACTURA COMERCIAL").size(25).bold().center());
        obj.add(new LineItem(" "));
        obj.add(new Line());
        obj.add(new LineItem(" "));

        ArrayList<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("Cantidad",20));
        columns.add(new TableColumn("Descripcion",50));
        columns.add(new TableColumn("Precio",30));

        ArrayList<TableCell> cells = new ArrayList<>();
        cells.add(new TableCell("1").center());
        cells.add(new TableCell("Montura 0516").center());
        cells.add(new TableCell("$1,200.00").right());

        cells.add(new TableCell("1").center());
        cells.add(new TableCell("Cristales Foto Grey").center());
        cells.add(new TableCell("$2,000.00").right());

        cells.add(new TableCell("1").center());
        cells.add(new TableCell("Examen de la vista").center());
        cells.add(new TableCell("$0.00").right());

        cells.add(new TableCell(" ").noBorder());
        cells.add(new TableCell(" ").noBorder());
        cells.add(new TableCell(" ").noBorder());


        cells.add(new TableCell(" ").noBorder());
        cells.add(new TableCell("Total:").right().noBorder());
        cells.add(new TableCell("$3,200.00").right().noBorder());

        obj.add(new Table(columns, cells));
        obj.add(new LineItem(" "));
        obj.add(new Line().thickness(2));
        obj.add(new LineItem(" "));
        obj.add(new LineItem("30 dias de garantia. Favor enviar esta factura al Email:opticaellocario@wepa.com con un dia de antelacion.").size(10).center());

        return obj;

    }


    public Invoice format2(){
        ////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////       HEADER         //////////////////////////////////////////////////
        Header h = new Header("Colchonera los Gorditos","Av Gordita esq. labatata #222","809-236-1503","chg@gmail.com", new Image(BitmapFactory.decodeResource(getResources(),R.drawable.colchon)));
        Client client = new Client("Jose Matos", "402-2181728-7","809-998-3580","calle los girasoles babababa");
        /////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////       ORDER         //////////////////////////////////////////////////
        ArrayList<OrderDetail> orderDetail = new ArrayList<>();
        for(int i =0; i<50; i++){
            orderDetail.add(new OrderDetail("2", "Cama Pillow Top 60", "$5,900.00", "$11,800.00"));
        }

        ArrayList<AmountsResume> amountsResume = new ArrayList<>();
        for(int i =0; i<50; i++){
            amountsResume.add(new AmountsResume("SubTotal:", "$11,800.00"));
            amountsResume.add(new AmountsResume("Transporte:", "$200.00"));
            amountsResume.add(new AmountsResume("Total:", "$12,000.00"));
        }


        Order order = new Order(orderDetail, amountsResume);
        /////////////////////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////       PAYMENTS        ///////////////////////////////////////////////////
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(new Payment("Efectivo", "$12,000.00"));

        Invoice invoice = new Invoice(h,"Factura Comercial", "esto es un footer", "No: 0001", "Fecha: "+new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date()),
                client,order,payments);
        //////////////////////////////////////////////////////////////////////////////////////////////

        return invoice;
    }




}
