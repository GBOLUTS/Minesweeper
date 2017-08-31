package com.company;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Гена on 22.04.17.
 */
public class GameField extends JFrame {
    Buttons useButton = new Buttons();
    //Статичные переменные
     static ArrayList<String> field = new ArrayList();
     static int NUMBER_OF_MINES;
     static int NUMBER_X;
     static int NUMBER_Y;
     static int NUMBER_OF_RIGHT_MINES=0;
     static int NUMBER_OF_WRONG_MINES = 0;
     static int NUMBER_OF_OPEN_FIELDS = 0;
     //Конструктор поля
    GameField (int nX, int nY, int nMines){
        super("Minesweeper by BG");
        NUMBER_OF_MINES = nMines;
        NUMBER_X = nX;
        NUMBER_Y = nY;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0, 0, 300, 300);
        setSize(nX*49, nY*49);
        setResizable(false);

        //Опредедение переменных
//        ArrayList field = new ArrayList();

        //Создание самого поля
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(nX,nY,0,0));
        //Определение мест расположения мин  и заполнения числа мин в List
        for (int i = 0; i < nX*nY; i++) {
           if (i < nMines){
               GameField.field.add("X");
           }else {GameField.field.add("");}
        }
        Collections.shuffle(GameField.field);
        addNumberOfMines(GameField.field, nX, nY);

        for (int i = 0; i < GameField.field.size(); i++){
            JButton button = useButton.addButton(i);
            panel.add(button);
            setContentPane(panel);

        }
    }
//Методы!!!
    public static ArrayList<Integer> findNumbersOfAdjFields(int pos, int nX, int nY){
         ArrayList<Integer> list = new ArrayList<Integer>();
         double div = (double)pos/nY;
         int cX =(int) Math.ceil(div);
         int cY = pos - (cX - 1)*nY;

        ArrayList <Integer> neiX = new ArrayList<>();
        ArrayList<Integer> neiY = new ArrayList<>();

        for (int i = cX - 1; i <cX + 2 ; i++) {//Определяем в каких рядах есть соседи
            if ((i>0)&&(i < nX +1)) {neiX.add(i);}
        }

        for (int i = cY - 1; i <cY + 2 ; i++) {//Определяем в каких столбцах есть соседи
            if ((i>0)&&(i < nY +1)) {neiY.add(i);}
        }

        for (int i = 0; i < neiX.size(); i++) {
            for (int j = 0; j < neiY.size(); j++) {
                int fX = neiX.get(i);
                int fY = neiY.get(j);
                int neighbour = (fX - 1) * nY + fY;
                if (neighbour!=pos){//Исключаем саму позицию из её соседей
                    list.add(neighbour);
                }
            }
        }
         return list;
        }

        public int findNumberOfMines (ArrayList list, int pos, int nX, int nY){
        int numberOfMines = 0;
        ArrayList<Integer> listOfNeighbours = findNumbersOfAdjFields(pos,nX,nY);
            for (int i = 0; i <listOfNeighbours.size() ; i++) {
                int numberOfPosition = listOfNeighbours.get(i);
                if (list.get(numberOfPosition - 1).equals("X")){
                    numberOfMines+=1;
                }
            }
        return numberOfMines;
        }

        public void addNumberOfMines(ArrayList list, int nX, int nY){
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals("X")){
                    int numberOfMines = findNumberOfMines(list, i +1, nX, nY);
                    String sNumberOfMines= String.valueOf(numberOfMines);//Без перевода в String возникает ошибка с форматами
                    list.set(i,sNumberOfMines);
                }
            }
        }
}
