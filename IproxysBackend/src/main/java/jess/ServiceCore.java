/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jess;

import spark.utils.IOUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author root
 */
public class ServiceCore {
    private static Rete engine;
    private static WorkingMemoryMarker marker;
    private static WorkingMemoryMarker marker2;
    private static WorkingMemoryMarker eraseData;
    private static ServiceCore servicecore = null;
    private  String ruleFilePath;
    private ServiceCore() throws JessException, FileNotFoundException, IOException, URISyntaxException {

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File temporaryFile = new File(tempDir, "templateCopy.dot");
        InputStream templateStream = getClass().getResourceAsStream("myTemplate.dot");
        IOUtils.copy(templateStream, new FileOutputStream(temporaryFile));
        ruleFilePath = temporaryFile.getAbsolutePath();

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

            engine.batch(ruleFilePath);
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
