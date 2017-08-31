package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.awt.event.MouseEvent.BUTTON1;

/**
 * Created by Гена on 22.04.17.
 */
public class Buttons extends JButton {

    ArrayList<JButton> listOfButtons = new ArrayList<JButton>();
    //Метод открытия соседних клеток
    public void pressAdjButtons (String  command, ArrayList<JButton> list){
        int pos = Integer.parseInt(command) + 1;
        ArrayList <Integer> numberOfAdjFields = GameField.findNumbersOfAdjFields(pos,GameField.NUMBER_X,GameField.NUMBER_Y);
        for (int i = 0; i < numberOfAdjFields.size(); i++) {//Уменьшил все позиции на 1, чтобы получить комманды
            int posit = numberOfAdjFields.get(i);
            numberOfAdjFields.set(i, posit - 1);
        }
        for (int i = 0; i < list.size(); i++) {
           String commandOfButton = list.get(i).getActionCommand();
           if (!commandOfButton.equals("X")){
               int numberOfCommand = Integer.parseInt(commandOfButton);
                    if (numberOfAdjFields.contains(numberOfCommand)){

                        list.get(i);
                    }
           }

        }
    }

        public void openAllFields (){
            for (int i = 0; i < listOfButtons.size(); i++) {
                String commandOfText = listOfButtons.get(i).getActionCommand();
                String text = GameField.field.get(Integer.parseInt(commandOfText));
                listOfButtons.get(i).setText(text);
                listOfButtons.get(i).setForeground(Color.BLACK);
            }
        }
        public void whenYouLose(){
            openAllFields();
            JOptionPane.showMessageDialog(null, "Вы проиграли!", "Сожалеем, Вы проиграли!", JOptionPane.WARNING_MESSAGE);
        }

        public void checkWin(){//Условие победы (либо открыты все без мин, либо правильно расставлены мины (и нет лишних))
           int numberOfAllNotMined = GameField.NUMBER_X*GameField.NUMBER_Y - GameField.NUMBER_OF_MINES;
           if ((GameField.NUMBER_OF_OPEN_FIELDS>=numberOfAllNotMined)||((GameField.NUMBER_OF_WRONG_MINES == 0)&&(GameField.NUMBER_OF_RIGHT_MINES==GameField.NUMBER_OF_MINES))){
               openAllFields();
               JOptionPane.showMessageDialog(null, "Вы Выиграли!", "Поздравляем, Вы выиграли!", JOptionPane.WARNING_MESSAGE);
           }
        }
    public JButton addButton(int answer) {
        JButton button = new JButton("");
        listOfButtons.add(button);
        button.setBackground(Color.white);
        String decodedAnswer =(String) GameField.field.get(answer);
        button.setActionCommand(String.valueOf(answer));


        if (decodedAnswer.equals("X"))
            {button.addMouseListener(new MineClicked());}
            else
                {button.addMouseListener(new NotMineClicked());}
        return button;
    }

    public class MineClicked implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            JButton button = (JButton) e.getSource();


            if (e.getButton() == BUTTON1) {//Левый клик мыши
               if(button.getForeground().equals(Color.red)){//Если ранее решили что тут мина
                   button.setForeground(Color.BLACK);
                   button.setText("?");
                   GameField.NUMBER_OF_RIGHT_MINES--;
               }else {
                   whenYouLose();
               }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {//Правый клик мыши
                if (button.getForeground().equals(Color.black)||button.getText().equals("")) {
                    button.setText("1");
                    button.setForeground(Color.red);
                    button.setBackground(Color.black);
                    GameField.NUMBER_OF_RIGHT_MINES++;
                    checkWin();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


    public class NotMineClicked implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            String command = button.getActionCommand();//Узнаём номер кнопки
            String decodedCommand = GameField.field.get(Integer.parseInt(command));
            if (decodedCommand.equals("0")){decodedCommand = "";}

            if (e.getButton() == BUTTON1) {//Левый клик мыши
                if (button.getForeground().equals(Color.RED)) {//Если раньше решили, что здесь мина
                    button.setText("?");
                    button.setForeground(Color.black);
                    GameField.NUMBER_OF_WRONG_MINES--;
                } else {
                    if (button.getText().equals("") || button.getText().equals("?")) {
                        button.setText(decodedCommand);
                        button.setForeground(Color.BLACK);
                        button.setBackground(Color.cyan);
                        if (decodedCommand.equals("")) {
                            pressAdjButtons(command, listOfButtons);
                        }
                        GameField.NUMBER_OF_OPEN_FIELDS++;
                        checkWin();
                    }
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (button.getText().equals("")||button.getText().equals("?")) {//Правый клик если заполнено и не ?, не изменяет
                    button.setText("1");
                    button.setForeground(Color.red);
                    GameField.NUMBER_OF_WRONG_MINES++;//Правый клик мыши
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

