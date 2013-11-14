/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.PersistenceData;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Luis
 */
public abstract class  PersistenceProvider {
    public static final String PERSISTENCE_UNIT= "TestJEE-ejbPU";
    protected static EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory(PersistenceProvider.PERSISTENCE_UNIT);
    protected static EntityManager entityManger = PersistenceProvider.entityFactory.createEntityManager();
    public static String findAll;
    public boolean save(){
        try{
            EntityTransaction currentTransaction = beginTransaction();
            PersistenceProvider.entityManger.persist(this);
            currentTransaction.commit();
            return true;
        }catch(Exception err){
            System.err.println(err.getMessage());
            return false;
        }
    }
    public void delete(){
        EntityTransaction currentTransaction = beginTransaction();
        PersistenceProvider.entityManger.remove(this);
        currentTransaction.commit();
    }
    
    public Object finbByID(long Id){
       return PersistenceProvider.entityManger.find(this.getClass(),Id);
    }
    
    public List<Object> findAll(){
        Query findAllNamedQuery = PersistenceProvider.entityManger.createNamedQuery(this.getClass().getSimpleName()+".findAll");
        return findAllNamedQuery.getResultList();
    }
            
    private EntityTransaction beginTransaction(){
         EntityTransaction CurrentTransaction = PersistenceProvider.entityManger.getTransaction();
         CurrentTransaction.begin();
         return CurrentTransaction;
    }
    
    public boolean update(){
        try{
            EntityTransaction currentTransaction = beginTransaction();
            PersistenceProvider.entityManger.refresh(this);
            currentTransaction.commit();
            return true;
        }catch(Exception err){
            System.err.println(err.getMessage());
            return false;
        }
    }
}