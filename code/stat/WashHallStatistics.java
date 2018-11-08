import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class WashHallStatistics implements Runnable
{
   WashHallDatabaseInterface dbinterface = null;
   private Socket client;

   public WashHallStatistics(Socket c) throws Exception
   {
      client = c;
      dbinterface = new WashHallDatabaseInterface();
   }

   public void run()
   {
      BufferedReader in = null;
      PrintWriter out = null;
      try
      {
         String data = null;
         String clientAddress = client.getInetAddress().getHostAddress();
         System.out.println("\r\nNew connection from " + clientAddress);
      
         in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
                );
         String input = in.readLine();
         StringTokenizer parse = new StringTokenizer(input);
         String method = parse.nextToken().toUpperCase();
         String fileRequested = parse.nextToken().toLowerCase();
         if (method.equals("GET"))
         {
            /*
               for(int i = 0; i < 1000; i++)
               {
            //Lets generate some data
            long minDay = LocalDate.of(2017, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2018, 11, 1).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            int washint = (int)(Math.random() * 5);
            String[] washType = { "earlyEconymyWash", "earlyStandardWash", "economy", "standard", "delux"};
            double[] washPrice = { 40, 64, 50, 80, 120 };
            dbinterface.addPurchase(new Purchase(0, (int)(Math.random() * 4), washType[washint], washPrice[washint], randomDate.atStartOfDay()));
            System.out.println(randomDate.atStartOfDay().toString());
               }
         
         */
            out = new PrintWriter(client.getOutputStream());
            System.out.println("Supported connection, returning statistics page");
            String page = getHTMLHeader("Wash hall statistics") + getJSYearlyChart() + getJSChart() + getJSPieChart();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            page += getMonthlyRevenueData(c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), "Monthly revenue chart", "Total income this month (so far)", "revenuemonthly", "washtypesmonthlypiecanvas" );
            c.add(Calendar.MONTH, -1);
            page += getMonthlyRevenueData(c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR), "Monthly revenue chart", "Total income last month", "revenuelastmonth", "washtypeslastmonthpiecanvas" );
            page += getYearlyRevenueData();
            page += getHTMLFooter();
            outData(out, page);
         }
      }
      catch(Exception e)
      {
         System.err.println("Server error : " + e);
      }
      finally
      {
         try
         {
            in.close();
            out.close();
            client.close();
         }
         catch(Exception e)
         {
            System.err.println("Error closing stream : " + e.getMessage());
         }
      }
   }

   private void outHeader(PrintWriter out, int length)
   {
      out.println("HTTP/1.1 200 OK");
      out.println("Server: Java HTTP Server from SSaurel : 1.0");
      out.println("Date: " + new Date());
      out.println("Content-type: text/html; charset=utf-8");
      out.println("Content-length: " + length);
      out.println(); // blank line between headers and content, very important !
      out.flush(); // flush character output stream buffer
   }

   private void outData(PrintWriter out, String s)
   {
      outHeader(out, s.length());
      out.println(s);
      out.flush();
   }

   private String getHTMLHeader(String title)
   {
      return("<!DOCTYPE html>" +
             "<html xmlns='http://www.w3.org/1999/xhtml'>" +
             "<head>" +
             "<title>" + title + "</title>" +
             "<meta charset='UTF-8' />" +
             "</head>" +
             "<body>");
   }

   private String getHTMLFooter()
   {
      return("</body></html>");
   }

   private String getJSYearlyChart()
   {
      return("<script>" +
             "var yearlychart = function(options){" +
             "    this.options = options;" +
             "    this.canvas = options.canvas;" +
             "    this.ctx = this.canvas.getContext('2d');" +
             "    this.colors = options.colors;" +
             "    this.draw = function(){" +
             "		this.ctx.fillStyle = 'black';" +
             "		this.ctx.fillRect(30, 0, 3, this.canvas.height);" +
             "		this.ctx.fillRect(0, this.canvas.height-30, this.canvas.width, 3);" +
             "        var xval, yval, ydefaultval;" +
             "		xval = 40;" +
             "		ydefaultval = this.canvas.height - 30;" +
             "		var color_index;" +
             "		color_index = 0;" +
             "		var i = 0;" +
             "		while(this.canvas.height - 30 - 10 - i > 0)" +
             "		{" +
             "			this.ctx.textAlign='right';" +
             "			this.ctx.fillStyle = 'black';" +
             "            this.ctx.fillText(i*3,25,this.canvas.height-i-30-5);" +
             "			i = i + 10;" +
             "		}" +
             "		for (categ in this.options.data){" +
             "			yval = ydefaultval;" +
             "           val = this.options.data[categ];" +
             "			this.ctx.fillStyle = 'black';" +
             "			this.ctx.textAlign='center';" +
             "          this.ctx.fillText(categ.substring(0, String(categ).length-5),xval+25,yval+15);" +
             "			for(cate in val)" +
             "			{" +
             "				this.ctx.fillStyle = this.colors[color_index%this.colors.length];" +
             "				this.ctx.fillRect(xval, yval, 50, -1 * (val[cate]/3));" +
             "				yval = yval - (val[cate]/3);" +
             "				color_index++;" +
             "			}" +
             "			color_index = 0;" +
             "			xval = xval + 54;" +
             "        }" +
             "    }" +
             "}" +
             "</script>");
   }

   private String getJSChart()
   {
      return(
             "<script>" +
             "var chart = function(options){" +
             "    this.options = options;" +
             "    this.canvas = options.canvas;" +
             "    this.ctx = this.canvas.getContext('2d');" +
             "    this.colors = options.colors;" +
             "    this.draw = function(){" +
             "		this.ctx.fillStyle = 'black';" +
             "		this.ctx.fillRect(30, 0, 3, this.canvas.height);" +
             "		this.ctx.fillRect(0, this.canvas.height-30, this.canvas.width, 3);" +
             "        var xval, yval, ydefaultval;" +
             "		xval = 40;" +
             "		ydefaultval = this.canvas.height - 30;" +
             "		var color_index;" +
             "		color_index = 0;" +
             "		var i = 0;" +
             "		while(this.canvas.height - 30 - 10 - i*5 > 0)" +
             "		{" +
             "			this.ctx.textAlign='right';" +
             "			this.ctx.fillStyle = 'black';" +
             "           this.ctx.fillText(i,25,this.canvas.height-i*5-30-5);" +
             "			i = i + 10;" +
             "		}" +
             "		for (categ in this.options.data){" +
             "			yval = ydefaultval;" +
             "            val = this.options.data[categ];" +
             "			this.ctx.fillStyle = 'black';" +
             "			this.ctx.textAlign='center';" +
             "            this.ctx.fillText(categ,xval+9,yval+15);" +
             "			for(cate in val)" +
             "			{" +
             "				this.ctx.fillStyle = this.colors[color_index%this.colors.length];" +
             "				this.ctx.fillRect(xval, yval, 20, -1 * val[cate] * 5);" +
             "				yval = yval - val[cate];" +
             "				color_index++;" +
             "			}" +
             "			color_index = 0;" +
             "			xval = xval + 25;" +
             "        }" +
             "    }" +
             "}" +
             "</script>"
             );
   }

   private String getJSPieChart()
   {
      return(
             "<script>" +
             "function drawPieSlice(ctx,centerX, centerY, radius, startAngle, endAngle, color ){" +
             "    ctx.fillStyle = color;" +
             "    ctx.beginPath();" +
             "    ctx.moveTo(centerX,centerY);" +
             "    ctx.arc(centerX, centerY, radius, startAngle, endAngle);" +
             "    ctx.closePath();" +
             "    ctx.fill();" +
             "}  " +
             "var Piechart = function(options){" +
             "    this.options = options;" +
             "    this.canvas = options.canvas;" +
             "    this.ctx = this.canvas.getContext('2d');" +
             "    this.colors = options.colors;" +
             "    this.draw = function(){" +
             "        var total_value = 0;" +
             "        var color_index = 0;" +
             "        for (var categ in this.options.data){" +
             "            var val = this.options.data[categ];" +
             "            total_value += val;" +
             "        }" +
             "        var start_angle = 0;" +
             "        for (categ in this.options.data){" +
             "            val = this.options.data[categ];" +
             "            var slice_angle = 2 * Math.PI * val / total_value;" +
             "            drawPieSlice(" +
             "                this.ctx," +
             "                this.canvas.width/2," +
             "                this.canvas.height/2," +
             "                Math.min(this.canvas.width/2,this.canvas.height/2)," +
             "                start_angle," +
             "                start_angle+slice_angle," +
             "                this.colors[color_index%this.colors.length]" +
             "            );" +
             "            start_angle += slice_angle;" +
             "            color_index++;" +
             "        }" +
             "        if (this.options.doughnutHoleSize){" +
             "            drawPieSlice(" +
             "                this.ctx," +
             "                this.canvas.width/2," +
             "                this.canvas.height/2," +
             "                this.options.doughnutHoleSize * Math.min(this.canvas.width/2,this.canvas.height/2)," +
             "                0," +
             "                2 * Math.PI," +
             "                '#ff0000'" +
             "            );" +
             "        }" +
             "		start_angle = 0;" +
             "		for (categ in this.options.data){" +
             "			val = this.options.data[categ];" +
             "			slice_angle = 2 * Math.PI * val / total_value;" +
             "			var pieRadius = Math.min(this.canvas.width/2,this.canvas.height/2);" +
             "			var labelX = this.canvas.width/2 + (pieRadius / 2) * Math.cos(start_angle + slice_angle/2);" +
             "			var labelY = this.canvas.height/2 + (pieRadius / 2) * Math.sin(start_angle + slice_angle/2);" +
             "			if (this.options.doughnutHoleSize){" +
             "				var offset = (pieRadius * this.options.doughnutHoleSize ) / 2;" +
             "				labelX = this.canvas.width/2 + (offset + pieRadius / 2) * Math.cos(start_angle + slice_angle/2);" +
             "				labelY = this.canvas.height/2 + (offset + pieRadius / 2) * Math.sin(start_angle + slice_angle/2);" +
             "			}" +
             "			if(val > 0)" +
             "			{" +
             "			var labelText = val;" +
             "			this.ctx.fillStyle = 'white';" +
             "			this.ctx.font = 'bold 20px Arial';" +
             "			this.ctx.fillText(labelText, labelX,labelY);" +
             "			start_angle += slice_angle;" +
             "			}" +
             "		}" +
             "   }" +
             "}" +
             "</script>"
             );
   }



   private String getMonthlyStatCanvas(String title, String incometitle, double income, String chartid, String piechartid)
   {
      return("<div style='display: inline-block'>" +
             "	<div style='font-size: 20px; font-weight: bold;'>"+title+"</div>" +
             "	<div style='font-size: 15px;'>"+ incometitle +": " + Double.toString(income) + "</div>" +
             "	<div style='float: left'>" +
             "		<canvas id='"+chartid+"' width='820' height='400'></canvas>" +
             "	</div>" +
             "	<div style='float: right'>" +
             "		<canvas id='"+piechartid+"' width='400' height='400'></canvas>" +
             "	</div>" +
             "	<div style='display: inline-block'>" +
             "		Shows the different washes: <div style='color: #fde23e; display: inline-block'>Early Bird Economy</div>, <div style='color: #f16e23; display: inline-block'>Early Bird Standard</div>, <div style='color: #57d9ff; display: inline-block'>Economy</div>, <div style='color: #937e88; display: inline-block'>Standard</div> & <div style='color: #0000FF; display: inline-block'>De luxe</div>" +
             "		. The pie chart, shows the sum of the different washtypes." +
             "	</div>" +
             "</div>" +
             "<br/><br/>");
   }


   /*
      We need data from the database to make this work!

      monthlyrevenu expects 'day of month' : {'Washtyp' ; amount} - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
      piemonthlywashtypedata expects the different washtypes, and their amount - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
      */
   private String getMonthlyRevenueData(int month, int year,  String title, String incometitle, String chartid, String piechartid)
   {
      String returndata = "";
      int[][] monthlydata = new int[31][5];
      int[] monthlywashtypeuse = new int[5];
      double totalincome = 0;
      for(int day = 0; day < 31; day++)
      {
         for(int i = 0; i < 5; i++)
         {
            monthlydata[day][i] = 0;
         }
      }
   
      for(int day = 0; day < 31; day++)
      {
         ArrayList<Purchase> p = dbinterface.getPurchasesDayMonthYear(day+1, month, year);
         for(int i = 0; i < p.size(); i++)
         {
            totalincome += p.get(i).getWashPrice();
            switch(p.get(i).getWashType())
            {
               case "earlyEconymyWash" :
                  monthlydata[day][0]++;
                  monthlywashtypeuse[0]++;
                  break;
               case "earlyStandardWash" :
                  monthlydata[day][1]++;
                  monthlywashtypeuse[1]++;
                  break;
               case "economy" :
                  monthlydata[day][2]++;
                  monthlywashtypeuse[2]++;
                  break;
               case "standard" :
                  monthlydata[day][3]++;
                  monthlywashtypeuse[3]++;
                  break;
               case "delux" :
                  monthlydata[day][4]++;
                  monthlywashtypeuse[4]++;
                  break;
               default:
                  System.out.println("Unknown washtype: " + p.get(i).getWashType());
            }
         }
      }
   
      //returndata = getMonthlyRevenueData("Monthly revenue chart", "Total income this month (so far)", "revenuemonthly", "washtypesmonthlypiecanvas" );
      returndata = getMonthlyStatCanvas(title, incometitle, totalincome, chartid, piechartid);
      returndata += "<script>" +
         "var monthlyrevenue = {";
   
      for(int day = 0; day < 31; day++)
      {
         returndata += "    '" + (day+1) +"': { 'EarlyBirdEconomy' : " + monthlydata[day][0] + ", 'EarlyBirdStandard' : " + monthlydata[day][1] + ", 'Economy' : " + monthlydata[day][2] + ", 'Standard' : " + monthlydata[day][3] + ", 'De Luxe' : " + monthlydata[day][4] + "},";
      }
      returndata += "    '31': { 'EarlyBirdEconomy' : " + monthlydata[30][0] + ", 'EarlyBirdStandard' : " + monthlydata[30][1] + ", 'Economy' : " + monthlydata[30][2] + ", 'Standard' : " + monthlydata[30][3] + ", 'De Luxe' : " + monthlydata[30][4] + "},";
   
      returndata += "};" +
         "var "+chartid+"obj = new chart(" +
         "    {" +
         "        canvas:"+chartid+"," +
         "        data:monthlyrevenue," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         chartid+"obj.draw();" +
         "var piemonthlywashtypedata = {" +
         "    'EarlyBirdEconomy': "+monthlywashtypeuse[0]+"," +
         "    'EarlyBirdStandard': "+monthlywashtypeuse[1]+"," +
         "    'Economy': "+monthlywashtypeuse[2]+"," +
         "    'Standard': "+monthlywashtypeuse[3]+"," +
         "	'De Luxe': " + monthlywashtypeuse[4] +
         "};" +
         "var "+piechartid+"obj = new Piechart(" +
         "    {" +
         "        canvas:"+piechartid+"," +
         "        data:piemonthlywashtypedata," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         piechartid+"obj.draw();" +
         "</script>";
   
      return(returndata);
   }

   /*
      We need data from the database to make this work!

      yearlyrevenue expects 'month' : {'Washtyp' ; amount} - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
      pieyearlywashtypedata expects the different washtypes, and their amount - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
      */
   private String getYearlyRevenueData()
   {
      String returndata = "<div style='display: inline-block'>" +
         "	<div style='font-size: 20px; font-weight: bold;'>Last 14 month chart</div>" +
         "	<div style='float: left'>" +
         "		<canvas id='revenueyearly' width='820' height='400'></canvas>" +
         "	</div>" +
         "	<div style='float: right'>" +
         "		<canvas id='washtypesyearlypiecanvas' width='400' height='400'></canvas>" +
         "	</div>" +
         "	<div style='display: inline-block'>" +
         "		Shows the different washes: <div style='color: #fde23e; display: inline-block'>Early Bird Economy</div>, <div style='color: #f16e23; display: inline-block'>Early Bird Standard</div>, <div style='color: #57d9ff; display: inline-block'>Economy</div>, <div style='color: #937e88; display: inline-block'>Standard</div> & <div style='color: #0000FF; display: inline-block'>De luxe</div>" +
         "		. The pie chart, shows the sum of the different washtypes.	" +
         "	</div>" +
         "</div>"  +
         "<script>" +
         "var yearlyrevenue = {";
      Calendar c = Calendar.getInstance();
      c.setTime(new Date());
      c.add(Calendar.MONTH, -13);
      int[][] monthlydata = new int[14][5];
      int[] monthlywashtypeuse = new int[5];
      double totalincome = 0;
      for(int months = 0; months < 14; months++)
      {
         for(int i = 0; i < 5; i++)
         {
            monthlydata[months][i] = 0;
         }
      }
      String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      for(int months = 0; months < 14; months++)
      {
         System.out.println("Date lookup" + monthNames[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
         ArrayList<Purchase> p = dbinterface.getPurchasesMonthYear(c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR));
         for(int i = 0; i < p.size(); i++)
         {
            totalincome += p.get(i).getWashPrice();
            switch(p.get(i).getWashType())
            {
               case "earlyEconymyWash" :
                  monthlydata[months][0]++;
                  monthlywashtypeuse[0]++;
                  break;
               case "earlyStandardWash" :
                  monthlydata[months][1]++;
                  monthlywashtypeuse[1]++;
                  break;
               case "economy" :
                  monthlydata[months][2]++;
                  monthlywashtypeuse[2]++;
                  break;
               case "standard" :
                  monthlydata[months][3]++;
                  monthlywashtypeuse[3]++;
                  break;
               case "delux" :
                  monthlydata[months][4]++;
                  monthlywashtypeuse[4]++;
                  break;
               default:
                  System.out.println("Unknown washtype: " + p.get(i).getWashType());
            }
         }
         c.add(Calendar.MONTH, 1);
      }
   
      c.add(Calendar.MONTH, -14);
      for(int months = 0; months < 13; months++)
      {
         returndata += "    '" + monthNames[c.get(Calendar.MONTH)] + "_" + c.get(Calendar.YEAR) + "': { 'EarlyBirdEconomy' : " + monthlydata[months][0] + ", 'EarlyBirdStandard' : " + monthlydata[months][1] + ", 'Economy' : " + monthlydata[months][2] + ", 'Standard' : " + monthlydata[months][3] + ", 'De Luxe' : " + monthlydata[months][4] + "},";
         c.add(Calendar.MONTH, 1);
      }
      returndata += "    '" + monthNames[c.get(Calendar.MONTH)] + "_" + c.get(Calendar.YEAR) +"': { 'EarlyBirdEconomy' : " + monthlydata[13][0] + ", 'EarlyBirdStandard' : " + monthlydata[13][1] + ", 'Economy' : " + monthlydata[13][2] + ", 'Standard' : " + monthlydata[13][3] + ", 'De Luxe' : " + monthlydata[13][4] + "}";
   
      returndata += "};" +
         "var chartyearlydata = new yearlychart(" +
         "    {" +
         "        canvas:revenueyearly," +
         "        data:yearlyrevenue," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "chartyearlydata.draw();" +
         "var pieyearlywashtypedata = {" +
         "    'EarlyBirdEconomy': "+monthlywashtypeuse[0]+"," +
         "    'EarlyBirdStandard': "+monthlywashtypeuse[1]+"," +
         "    'Economy': "+monthlywashtypeuse[2]+"," +
         "    'Standard': "+monthlywashtypeuse[3]+"," +
         "	'De Luxe': " + monthlywashtypeuse[4] +
         "};" +
         "var yearlywashtypes = new Piechart(" +
         "    {" +
         "        canvas:washtypesyearlypiecanvas," +
         "        data:pieyearlywashtypedata," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "yearlywashtypes.draw()" +
         "</script>";
      return(returndata);
   }

   public static void main(String[] args) throws Exception
   {
      try {
         //We listen on port 80 (normal webserver port)
         ServerSocket serverConnect = new ServerSocket(80);
         // we listen until user halts server execution
         while (true)
         {
            //When we get a new connection we create a new Socket to talk to the client. and create an object of the type MyServerSocket
            WashHallStatistics myServer = new WashHallStatistics(serverConnect.accept());
            System.out.println("Connecton opened. (" + new Date() + ")");
            // create a dedicated thread to manage the client connection
            Thread thread = new Thread(myServer);
            thread.start();
         }
      
      }
      catch (IOException e)
      {
         System.err.println("Server Connection error : " + e.getMessage());
      }
   }
}
