package org.ssa.ironyard.model;

import java.math.BigDecimal;

import org.ssa.ironyard.model.DomainObject;


public class Account implements DomainObject
{
    
    Type type;
    Customer customer;
    Integer id;
    BigDecimal balance;
    boolean isLoaded = false;


    public Account(int id, Customer customer, Type type, BigDecimal balance)
    {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.balance = balance;
        
        
    }
    
    public Account(Customer customer, Type type, BigDecimal balance)
    {
        this.customer = customer;
        this.type = type;
        this.balance = balance;
    }
    
    public Account(){}
    


    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Customer getCustomer()
    {
        return customer;
    }
   
    public void setLoaded(boolean isLoaded)
    {
        this.isLoaded = isLoaded;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }
    
    public enum Type
    {
        CHECKING("CH"), SAVINGS("SA");
        public final String abbrev;
        
        private Type(String abbrev)
        {
            this.abbrev = abbrev;
        }
        
        public static Type getInstance(String abbrev)
        {
            for(Type type : values())
            {
                if(type.abbrev.equals(abbrev))
                    return type;
            }
            return null;
        }
    }
    
    @Override
    public Account clone()
    {
        try
        {
            Account copy = (Account) super.clone();
            copy.setCustomer(this.customer.clone());
            return copy;
        }
        catch(CloneNotSupportedException ex)
        {
            
        }
        return null;
        
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((balance == null) ? 0 : balance.hashCode());
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    
    @Override 
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return this.id.equals(other.getId());
    }
    
  
//    public boolean deeplyEquals(Object obj)
//    {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Account other = (Account) obj;
//        return this.id == other.id && this.customer.equals(other.customer) && this.type == other.type && this.balance.compareTo(other.balance)==0;
//    }


    

    
    

    
    
}
