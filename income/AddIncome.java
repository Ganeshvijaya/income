import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddIncome extends HttpServlet 
{
   //Declarations of required attributes
    int Mem_id,Mem_income;
    

    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
			HttpSession session= request.getSession(false);
		if(session!=null){
			String id = (String) session.getAttribute("user_id");

		    out.print("You may proceed User "+id);
		    }
		    else{
		        out.print("Please login first");
		        request.getRequestDispatcher("index.html").include(request, response);
			}
           
              getParmas(request);//function to get parameters from request and 
               validateAddincome();

    }

    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
                  doGet(request, response);    //to handle post request
    }
    
    public void getParmas(HttpServletRequest request)// method to get paramters and intilise the attributes
    {
         Mem_id=Integer.parseInt(request.getParameter("Mid"));
         Mem_income=Integer.parseInt(request.getParameter("income"));
         
         
    }
    
    public void validateAddincome() //method to addincome
    {   
        try
        {
        Connection con=getConnection();
        PreparedStatement pst1=getStatement();
        int k=pst1.executeUpdate();
        if(k==1){
      /*  System.out.println("Income Added Sucessfully ");
        RequestDispatcher rs = request.getRequestDispatcher("Addincome.html");*/
		//out.println("Income Added Successfully");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Income Added Successfully');");
			out.println("location='incomeEntry.html';");
			out.println("</script>");
		}
        }
        catch(Exception ex)
        { 
             System.out.println(ex);
        }
        
    }
    
    public  Connection getConnection()//method to make database connections
    {
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:8089/ExpenseTracker","root","pooja18#");
            System.out.println("Connected.......");
            }
            catch(Exception op){op.printStackTrace();}
        return con;
    }
    
    public PreparedStatement getStatement()//method to prepare a statement
    { 
        PreparedStatement pst1=con.prepareStatement("Update account set balance = balance + ? where Accmem_id = ?");
        pst1.setInt(1,Mem_income);
        pst1.setString(2,Mem_id);
        return pst1;
    }
    
}