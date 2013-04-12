/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.jess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.Filter;
import jess.JessException;
import jess.Rete;
import jess.WorkingMemoryMarker;

/**
 *
 * @author root
 */
public class ServiceCore {
    private static Rete engine;
    private static WorkingMemoryMarker marker;
    private static WorkingMemoryMarker marker2;
    private static WorkingMemoryMarker eraseData;
    private static ServiceCore servicecore = null;
    
    private ServiceCore() throws JessException, FileNotFoundException, IOException{
        Configure();
    
    }
    
    public static ServiceCore getInstance(){
        if(servicecore == null )
        {
            try{
                servicecore = new ServiceCore();
            }catch(Exception ex){
                System.err.println(ex);
            }
        }
        return servicecore;
    
    }
    private void Configure() throws JessException, FileNotFoundException, IOException {
        engine = new Rete();
	//engine.reset();
        engine.batch("/home/ljpena/NetBeansProjects/Iproxy Server/jess_rules.clp"); 
        marker = engine.mark();//para volver a este punto facilmente check point
        //eraseData = engine.mark();
    }

    public void addList(Object[] o) {
     //   marker = engine.mark();
        try {
       //     engine.reset();
            for(int i = 0; i < o.length; i++)
            engine.add(o[i]);
       //     marker2 = engine.mark();
        } catch (JessException ex) {
            System.out.println(ex);
        }
       
    
    }
    public void addObject(Object o) throws JessException{
        //engine.reset();
       // marker = engine.mark();
    //    engine.reset();
        engine.add(o);
        /*if (o instanceof iproxys.PersistenceData.SummaryIP_BandWidth) {
            engine.add((iproxys.PersistenceData.SummaryIP_BandWidth) o);
        } 
        else if (o instanceof iproxys.PersistenceData.SummaryIPPort_BandWidth) {
            engine.add((iproxys.PersistenceData.SummaryIPPort_BandWidth) o);
        } 
        else {
            engine.add((iproxys.PersistenceData.SummaryPort_BandWidth) o);
        }*/

        //marker2 = engine.mark();
    }
    public List<JessSuggestions> GetAllSuggestions(){
	List<JessSuggestions> sugestionList = new ArrayList<JessSuggestions>();
        try{	
            engine.run();
            Iterator sugestions = engine.getObjects(new Filter.ByClass(JessSuggestions.class));//saca del buffer puntero al buffer de ese tipo
        
            for (Iterator<JessSuggestions> iter = sugestions; iter.hasNext();) {
                    sugestionList.add(iter.next());
            }
        }catch(Exception ex){
            System.out.println(ex);
                
        }finally{
            return sugestionList;
        }
        
        
        
    }
    
    public void eraseData(){
        try {
            engine.resetToMark(marker);
        } catch (JessException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
