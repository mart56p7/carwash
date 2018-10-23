//The basecode for this java program is taken from https://www.pegaxchange.com/2017/12/07/simple-tcp-ip-server-client-java/ and https://medium.com/@ssaurel/create-a-simple-http-web-server-in-java-3fc12b29d5fd and then modified to meet our needs.

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
public class WashHallStatistics implements Runnable
{
   WashHallDatabaseInterface dbinterface = null;
   private Socket client;
   
   /**
   Constructor, the socket we get here, is the "connection" from the client
   */
   public WashHallStatistics(Socket c) throws Exception
   {
      client = c;
      dbinterface = new WashHallDatabaseInterface();
   }

   
   /**
   After we get a connection from the client, we have initialized a new object of MyServerSocket and we talk to the client through this method.
   */
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
         // get first line of the request from the client
         String input = in.readLine();
      	// we parse the request with a string tokenizer
         StringTokenizer parse = new StringTokenizer(input);
         String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
      	// we get file requested
         String fileRequested = parse.nextToken().toLowerCase();
         if (method.equals("GET"))
         {
            out = new PrintWriter(client.getOutputStream());
            System.out.println("Supported connection, returning statistics page");
            String page = getHTMLHeader("Wash hall statistics") + getJSYearlyChart() + getJSChart() + getJSPieChart() + getCanvasAreas(0, 0) + getMonthlyRevenueData() + getLastMonthsRevenueData() + getYearlyRevenueData() + getHTMLFooter();
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

   /**
   This is there to print header information to client
   */
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
   
   /**
   Here we write the data to our client. The s is a String with html in it. So the entire page is wrapped inside s.
   */
   private void outData(PrintWriter out, String s)
   {
      outHeader(out, s.length());
      out.println(s);
      out.flush(); // flush character output stream buffer
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
         "            this.ctx.fillText(i*10,25,this.canvas.height-i-30-5);" +
         "			i = i + 10;" +
         "		}" +
         "		for (categ in this.options.data){" +
         "			yval = ydefaultval;" +
         "           val = this.options.data[categ];" +
         "			this.ctx.fillStyle = 'black';" +
         "			this.ctx.textAlign='center';" +
         "            this.ctx.fillText(categ,xval+25,yval+15);" +
         "			for(cate in val)" +
         "			{" +
         "				this.ctx.fillStyle = this.colors[color_index%this.colors.length];" +
         "				this.ctx.fillRect(xval, yval, 50, -1 * (val[cate]/10));" +
         "				yval = yval - (val[cate]/10);" +
         "				color_index++;" +
         "			}" +
         "			color_index = 0;" +
         "			xval = xval + 60;" +
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
         "		while(this.canvas.height - 30 - 10 - i > 0)" +
         "		{" +
         "			this.ctx.textAlign='right';" +
         "			this.ctx.fillStyle = 'black';" +
         "           this.ctx.fillText(i,25,this.canvas.height-i-30-5);" +
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
         "				this.ctx.fillRect(xval, yval, 20, -1 * val[cate]);" +
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
         "			var labelText = Math.round(100 * val / total_value);" +
         "			this.ctx.fillStyle = 'white';" +
         "			this.ctx.font = 'bold 20px Arial';" +
         "			this.ctx.fillText(labelText+'%', labelX,labelY);" +
         "			start_angle += slice_angle;" +
         "		}" +
         "   }" +
         "}" +
         "</script>"
         );
   }
   
   private String getCanvasAreas(double thismonthincome, double lastmonthincome)
   {
      return(
         "<div style='display: inline-block'>" +
         "	<div style='font-size: 20px; font-weight: bold;'>Monthly revenue chart</div>" +
         "	<div style='font-size: 15px;'>Total income this month (so far): " + Double.toString(thismonthincome) + "</div>" +
         "	<div style='float: left'>" +
         "		<canvas id='revenuemonthly' width='820' height='400'></canvas>" +
         "	</div>" +
         "	<div style='float: right'>" +
         "		<canvas id='washtypesmonthlypiecanvas' width='400' height='400'></canvas>" +
         "	</div>" +
         "	<div style='display: inline-block'>" +
         "		Shows the different washes: <div style='color: #fde23e; display: inline-block'>Early Bird Economy</div>, <div style='color: #f16e23; display: inline-block'>Early Bird Standard</div>, <div style='color: #57d9ff; display: inline-block'>Economy</div>, <div style='color: #937e88; display: inline-block'>Standard</div> & <div style='color: #0000FF; display: inline-block'>De luxe</div>" +
         "		. The pie chart, shows the sum of the different washtypes." +
         "	</div>" +
         "</div>" +
         "<br/><br/>" +
         "<div style='display: inline-block'>" +
         "	<div style='font-size: 20px; font-weight: bold;'>Last months revenue chart</div>" +
         "	<div style='font-size: 15px;'>Total income last month: " + Double.toString(lastmonthincome) + "</div>" +
         "	<div style='float: left'>" +
         "		<canvas id='revenuelastmonth' width='820' height='400'></canvas>" +
         "	</div>" +
         "	<div style='float: right'>" +
         "		<canvas id='washtypeslastmonthpiecanvas' width='400' height='400'></canvas>" +
         "	</div>" +
         "	<div style='display: inline-block'>" +
         "		Shows the different washes: <div style='color: #fde23e; display: inline-block'>Early Bird Economy</div>, <div style='color: #f16e23; display: inline-block'>Early Bird Standard</div>, <div style='color: #57d9ff; display: inline-block'>Economy</div>, <div style='color: #937e88; display: inline-block'>Standard</div> & <div style='color: #0000FF; display: inline-block'>De luxe</div>" +
         "		. The pie chart, shows the sum of the different washtypes." +
         "	</div>" +
         "</div>" +
         "<br/><br/>" +
         "<div style='display: inline-block'>" +
         "	<div style='font-size: 20px; font-weight: bold;'>Yearly revenue chart</div>" +
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
         "</div>"  
         );
   }
   
   
   /**
   We need data from the database to make this work!
   
   monthlyrevenu expects 'day of month' : {'Washtyp' ; amount} - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   piemonthlywashtypedata expects the different washtypes, and their amount - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   */
   private String getMonthlyRevenueData()
   {
      String returndata = "";
      int[][] monthlydata = new int[31][5];
      for(int day = 1; day < 32; day++)
      {
         ArrayList<Purchase> p = dbinterface.getPurchasesDayMonthYear(day, 10, 2018);
         for(int i = 0; i < p.size(); i++)
         {
            switch(p.get(i).getWashType())
            {
               case "earlyEconymyWash" :
                  break;       
               case "earlyStandardWash" :
                  break;
               case "economy" :
                  break;
               case "standard" :
                  break;
               case "delux" :
                  break;           
               default:
                  System.out.println("Unknown washtype: " + p.get(i).getWashType());   
            }
         }
      }
      return(
         "<script>" +
         "var monthlyrevenue = {" +
         "    '1': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10, 'Economy' : 205, 'Standard' : 30, 'De Luxe' : 2}," +
         "    '2': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '3': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '4': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'5': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '6': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '7': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '8': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '9': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'10': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '11': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '12': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '13': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '14': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'15': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '16': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '17': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '18': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '19': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'20': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '21': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '22': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '23': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '24': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'25': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '26': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '27': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '28': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '29': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'30': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "	'31': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}" +
         "};" +
         "var chartmonthlydata = new chart(" +
         "    {" +
         "        canvas:revenuemonthly," +
         "        data:monthlyrevenue," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "chartmonthlydata.draw();" +
         "var piemonthlywashtypedata = {" +
         "    'EarlyBirdEconomy': 10," +
         "    'EarlyBirdStandard': 14," +
         "    'Economy': 2," +
         "    'Standard': 12," +
         "	'De Luxe': 1" +
         "};" +
         "var myPiechart = new Piechart(" +
         "    {" +
         "        canvas:washtypesmonthlypiecanvas," +
         "        data:piemonthlywashtypedata," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "myPiechart.draw();" +
         "</script>"
         );
   }
   
   /**
   We need data from the database to make this work!
   
   lastmonthrevenue expects 'day of month' : {'Washtyp' ; amount} - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   pielastmonthwashtypedata expects the different washtypes, and their amount - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   */
   private String getLastMonthsRevenueData()
   {
      return(
         "<script>" +
         "var lastmonthrevenue = {" +
         "    '1': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10, 'Economy' : 205, 'Standard' : 30, 'De Luxe' : 2}," +
         "    '2': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '3': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '4': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'5': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '6': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '7': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '8': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '9': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'10': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '11': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '12': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '13': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '14': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'15': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '16': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '17': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '18': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '19': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'20': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '21': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '22': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 805}," +
         "    '23': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '24': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'25': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    '26': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    '27': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    '28': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    '29': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'30': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "	'31': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}" +
         "};" +
         "var chartlastmonthdata = new chart(" +
         "    {" +
         "        canvas:revenuelastmonth," +
         "        data:lastmonthrevenue," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "chartlastmonthdata.draw();" +
         "var pielastmonthwashtypedata = {" +
         "   'EarlyBirdEconomy': 10," +
         "    'EarlyBirdStandard': 74," +
         "    'Economy': 2," +
         "    'Standard': 12," +
         "	'De Luxe': 50" +
         "};" +
         "var lastmonthwashtypes = new Piechart(" +
         "    {" +
         "        canvas:washtypeslastmonthpiecanvas," +
         "        data:pielastmonthwashtypedata," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "lastmonthwashtypes.draw()" +
         "</script>"      
         );
   } 

   /**
   We need data from the database to make this work!
   
   yearlyrevenue expects 'month' : {'Washtyp' ; amount} - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   pieyearlywashtypedata expects the different washtypes, and their amount - NOTE: The order the washtypes are in, matters! Go after  Early Bird Economy, Early Bird Standard, Economy, Standard & De luxe
   */   
   private String getYearlyRevenueData()
   {
      return(
         "<script>" +
         "var yearlyrevenue = {" +
         "    'January': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10, 'Economy' : 2005, 'Standard' : 30, 'De Luxe' : 2}," +
         "    'February': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    'March': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    'April': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'May': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 30}," +
         "    'June': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    'July': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}," +
         "    'August': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 20}," +
         "    'September': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 25}," +
         "	'October': { 'EarlyBirdEconomy' : 500, 'EarlyBirdStandard' : 3000}," +
         "    'November': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 10}," +
         "    'December': { 'EarlyBirdEconomy' : 5, 'EarlyBirdStandard' : 15}" +
         "};" +
         "var chartyearlydata = new yearlychart(" +
         "    {" +
         "        canvas:revenueyearly," +
         "        data:yearlyrevenue," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "chartyearlydata.draw();" +
         "var pieyearlywashtypedata = {" +
         "    'EarlyBirdEconomy': 10," +
         "    'EarlyBirdStandard': 50," +
         "    'Economy': 2," +
         "    'Standard': 12," +
         "	'De Luxe': 1" +
         "};" +
         "var yearlywashtypes = new Piechart(" +
         "    {" +
         "        canvas:washtypesyearlypiecanvas," +
         "        data:pieyearlywashtypedata," +
         "        colors:['#fde23e','#f16e23', '#57d9ff','#937e88', '#0000FF']" +
         "    }" +
         ");" +
         "yearlywashtypes.draw()" +
         "</script>"
         );
   }
   
   public static void main(String[] args) throws Exception
   {
      try {
         //We listen on port 80 (normal webserver port)
         ServerSocket serverConnect = new ServerSocket(80);
      	// we listen until user halts server execution
         while (true) {
            //When we get a new connection we create a new Socket to talk to the client. and create an object of the type MyServerSocket
            WashHallstatistics myServer = new WashHallstatistics(serverConnect.accept());
            System.out.println("Connecton opened. (" + new Date() + ")");			
         	// create a dedicated thread to manage the client connection
            Thread thread = new Thread(myServer);
            thread.start();
         }
      	
      } catch (IOException e) {
         System.err.println("Server Connection error : " + e.getMessage());
      }
   }
    
    
}