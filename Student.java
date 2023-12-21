public class Student {
	
	private String name;
	   private String id;

	   public Student ()
	   {
	      name = "";
	      id   = "";
	   }

	   public Student (String name, String id)
	   throws StudentException
	   {
	     
	      name = name.trim ();
	      id   = id.trim ();

	      if (name.length () == 0)
	      {
	         
	         throw new StudentException ("Error: name cannot be blank.");
	      }

	      else if (id.length () == 0)
	      {
	       
	         throw new StudentException ("Error: Id cannot be blank.");
	      }

	      else
	      {
	        
	         this.name = name;
	         this.id   = id;
	      }
	   }

	   public String getName ()
	   {
	      return name;
	   }

	   public String getId ()
	   {
	      return id;
	   }

	   public void setName (String name)
	   {
	      this.name = name;
	   }

	   public void setId (String id)
	   {
	      this.id = id;
	   }

	   @Override
	   public String toString ()
	   {
	      return id + "\t" + name + "\t";

}
}
