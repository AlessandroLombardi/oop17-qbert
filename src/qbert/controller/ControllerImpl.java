package qbert.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jdom2.JDOMException;

import qbert.controller.input.Command;
import qbert.model.LevelSettings;
import qbert.model.models.GUILogic;
import qbert.view.Renderable;
import qbert.view.View;
import qbert.view.ViewImpl;

/**
 * The implementation of {@link Controller}.
 */
public class ControllerImpl implements Controller {

    private final String urlFile = "res/ranking.txt";
    private LevelConfigurationReader lcr;
    private final GameEngine gameEngine;
    private final GameStatusManager statusManager;
    
    private BlockingQueue gamePoint = new ArrayBlockingQueue<>(1);

    private final View view;

    /**
     * @param firstGameStatus the first application's {@link GameStatus}
     */
    public ControllerImpl(final GameStatus firstGameStatus) {
        this.lcr = new LevelConfigurationReaderImpl();
        this.statusManager = new GameStatusManager(firstGameStatus, this);

        this.view = new ViewImpl(this);
        this.gameEngine = new GameEngine(this.view);
    }

    @Override
    public final void setupGameEngine() {
        this.changeScene(this.statusManager.getCurrentStatus());
        this.gameEngine.mainLoop();
    }

    @Override
    public final LevelSettings getLevelSettings(final int level, final int round) {
        this.lcr = new LevelConfigurationReaderImpl();
        try {
            lcr.readLevelConfiguration(level, round);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return lcr.getLevelSettings();
    }

    @Override
    public final void notifyCommand(final Command command) {
        this.gameEngine.notifyCommand(command);
    }

    @Override
    public final void changeScene(final GameStatus newGameStatus) {
        this.statusManager.setCurrentStatus(newGameStatus);
        this.view.setScene(this.statusManager.getCurrentStatus().name());
        this.gameEngine.setup(this.statusManager.getModel());
    }

    @Override
    public final List<GUILogic> getGUI() {
        return this.statusManager.getModel().getGUI();
    }

    @Override
    public final List<Renderable> getRenderables() {
        return this.statusManager.getModel().getRenderables();
    }
    
    public void setScore(Integer i) {
        gamePoint.clear();
        gamePoint.add(i);
    }
    
    public Integer getScore() {
        return (Integer)gamePoint.poll();
    }
    
    public List<Map<String,Integer>> getRank() {
        List<Map<String,Integer>> rank = new ArrayList<Map<String,Integer>>();
        //Read file
        try (BufferedReader br = new BufferedReader(new FileReader(urlFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, Integer> mapTmp = new TreeMap<String, Integer>();
                mapTmp.put(line.split(":")[0],Integer.parseInt(line.split(":")[1]));
                rank.add(mapTmp);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //Order list 
        Collections.sort(rank, new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Map<String, Integer> map1, Map<String, Integer> map2) {
                Integer val1 = 0;
                Integer val2 = 0;
                for (Map.Entry<String, Integer> entry : map1.entrySet()) {
                    val1 = entry.getValue();
                }
                for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                    val2 = entry.getValue();
                }
                return val2.compareTo(val1);
            }
        });
        
        return rank;
    }
    
    public void addRank(String s, Integer i) {
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(urlFile, true));
            output.append("\r\n"+s+":"+i);
            output.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
