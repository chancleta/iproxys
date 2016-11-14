/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author root
 */
public class ServiceCore {
    private static Rete engine;
    private static WorkingMemoryMarker marker;
    private static WorkingMemoryMarker marker2;
    private static WorkingMemoryMarker eraseData;
    private static ServiceCore servicecore = null;
    private  Path myFolderPath;
    private ServiceCore() throws JessException, FileNotFoundException, IOException, URISyntaxException {

        final URI uri = getClass().getResource("/jess_rules.clp").toURI();
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        FileSystem zipfs = FileSystems.newFileSystem(uri, env);
        myFolderPath  = Paths.get(uri);

        Configure();

    }

    public static ServiceCore getInstance() {
        if (servicecore == null) {
            try {
                servicecore = new ServiceCore();
                System.out.println("creating jess instance");
            } catch (Exception ex) {
                System.err.println(ex);
            }

        }
        return servicecore;

    }

    private void Configure() {


        engine = new Rete();
        //engine.reset();
        try {

            engine.batch(myFolderPath.toFile().getAbsolutePath());
//        System.out.println((Paths.get(ServiceCore.class.getClass().getResource("/jess_rules.clp").toURI()).toFile().getAbsolutePath()));
//        marker = engine.mark();//para volver a este punto facilmente check point
            //eraseData = engine.mark();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addList(Object[] o) {
        //   marker = engine.mark();
        try {
            //     engine.reset();
            for (int i = 0; i < o.length; i++)
                engine.add(o[i]);
            //     marker2 = engine.mark();
        } catch (JessException ex) {
            ex.printStackTrace();
        }


    }

    public void addObject(Object o) throws JessException {
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

    public List<JessSuggestions> GetAllSuggestions() {
        List<JessSuggestions> sugestionList = new ArrayList<>();
        try {
            engine.run();
            Iterator sugestions = engine.getObjects(new Filter.ByClass(JessSuggestions.class));//saca del buffer puntero al buffer de ese tipo

            for (Iterator<JessSuggestions> iter = sugestions; iter.hasNext(); ) {
                sugestionList.add(iter.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            return sugestionList;
        }


    }

    public void eraseData() {

//            engine.resetToMark(marker);
        Configure();

    }
}
