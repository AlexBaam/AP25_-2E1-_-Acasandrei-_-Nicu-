package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    private final int canvasWidth = 800;
    private final int canvasHeight = 650;
    private final int dotRadius = 6;


    private final GameLogic gameLogic = new GameLogic();

    // Lista cu punctele de pe canva
    private List<Point> dotsLocations = new ArrayList<>();
    // Lista cu liniile desenate (Pentru retained mode)
    private List<Line> lines = new ArrayList<>();


    // Punctul curent selectat (Ptr click a doua puncte sau drag la linie
    private Point currentDot = null; // Ptr interactiuni mouse
    private int currentPlayer = 0; //Folosim 0 ptr rosu, 1 pentru albastru

    // Double buffering: Folosit pentru a imbunatati cum este realizat desenatul
    // Adica: Desenul intai este realizat in memorie, apoi apare linia desenata, atunci cand se reface render la canva
    private BufferedImage offscreen;
    private Graphics2D offscreenGraphics;


    private List<Point> dots = new ArrayList<>();

    private GraphForGame analyzer = new GraphForGame(dotsLocations, lines); // Graf de verificare daca e gata jocul

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight)); // Marimi fixe ptr canvas
        createOffscreenImage(); // Initializare imagine din memorie (Buffer)
        initMouseListeners(); // Initializare functionabilitate mouse
        dotsLocations = new ArrayList<>();
    }


    // Creaza imaginea in memorie si o umple cu alb
    private void createOffscreenImage() {
        offscreen = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        offscreenGraphics = offscreen.createGraphics();
        offscreenGraphics.setBackground(Color.WHITE);
        offscreenGraphics.fillRect(0, 0, canvasWidth, canvasHeight);
        offscreenGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    // Aici se realizeaza functionarea mouse
    // Adica dam Override la mousePressed (metoda) din MouseAdapter (clasa) ce verifica
    // daca unul din butoanele de pe mouse au fost apasate
    private void initMouseListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){

                // Luam pct pe care s-a dat click
                Point clickedPoint = getDotAt(e.getX(), e.getY());
                //e.getX() returneaza X din canva ptr mouse event, y la fel
                //Daca punctul pe care am apasat nu este null
                if (clickedPoint != null) {
                    //Verificam daca acesta este primul punct
                    if(currentDot == null) {
                        currentDot = clickedPoint; // Daca da, atribuim
                        //Daca current dot este null inseamna ca nu avem punct de start
                    } else if(!currentDot.equals(clickedPoint)) {
                    if(!lineExists(currentDot, clickedPoint)){
                        //Adaugam linia intre cele doua puncte, prima data in memorie, apoi o si desenam
                        Color color = (currentPlayer == 0 ? Color.RED : Color.BLUE);
                        lines.add(new Line(currentDot, clickedPoint, color));
                        drawLine(currentDot, clickedPoint, color);

                        gameLogic.addLine(currentDot, clickedPoint, currentPlayer);

                        frame.getScoreboard().updateRedScore(gameLogic.getRedScore());
                        frame.getScoreboard().updateBlueScore(gameLogic.getBlueScore());

                        analyzer.setLines(lines);
                        if(analyzer.isConnected())
                        {
                            gameOver();
                        }

                        currentPlayer = 1 - currentPlayer; // Switch la player (Daca el e pe 0, devine 1,
                        // daca el e pe 1, devine 0)
                    }
                    currentDot = null; // Resetam selectia
                    repaint(); // Reafisam canva
                    }
                }
            }
        });
    }

    private void gameOver() {
        double redScore = gameLogic.getRedScore();
        double blueScore = gameLogic.getBlueScore();

        String result;
        if(redScore > blueScore){
            result = "Blue wins!";
        } else if(blueScore > redScore) {
            result = "Red wins!";
        } else {
            result = "Draw!";
        }

        String endMessage = String. format("Game Over!\nRed score: %.2f\nBlue score: %.2f\nResult: %s", redScore, blueScore, result);

        JOptionPane.showMessageDialog(frame, endMessage);
    }

    // Verificam daca linia intre doua locatii exista deja
    // Trebuie sa verificam daca aceasta este in ambele sensuri pentru a ne asigura
    // ca nu putem desena peste o linie existenta din directia opusa
    private boolean lineExists(Point currentDot, Point clickedPoint) {
        for (Line line : lines) {
            if(((line.start.equals(currentDot)) && (line.end.equals(clickedPoint)))
                || ((line.start.equals(clickedPoint)) && (line.end.equals(currentDot)))){
                return true;
            }
        }
        return false;
    }

    /**
     * Returneaza un punct din lista aflat la coordonatele x si y
     * Sau null daca nu exista niciun punct destul de aproape
     */
    private Point getDotAt(int x, int y) {
        for(Point dot: dotsLocations) {
            if(dot.distance(x, y) < dotRadius) {
                // De ce verific dinstanta intre x,y si punctul din lista de puncte?
                // DACA DISTANTA DINTRE ORICARE PUNCT SI TUPLA X,Y OFERITA DE MOUSE ESTE MAI MARE DE 6 ATUNCI NU ESTE PUNCT ACOLO
                // Pentru exatitate, facem <3
                // DAR PUNCTELE NOASTRE AU COORDONATE X,Y ce sunt pe un pixel, DAR NU AU DOAR UN PIXEL MARIME, ASA CA TREBUIE CEVA MAI MARE
                // CEVA CE SA ACOPERE INTREAGA RAZA A CERCULUI
                return dot;
            }
        }
        return null;
    }
    // Distance este o metoda din Point2D care returneaza distanta dintre
    // un punct speficicat de tip Point2D si cele doua coordonate date


    /**
     * Primeste lista de puncte si le deseneaza
     */
    public void fillWithDots(List<Point> newDots) {
        this.dotsLocations = newDots; // Generam noua lista de locatii
        this.lines.clear(); // Stergem liniile vechi
        this.currentDot = null;// Resetam pct selectat
        this.currentPlayer = 0;

        createOffscreenImage(); // Cream o noua imagine in memorie
        drawAll();
        repaint();

        analyzer =  new GraphForGame(dotsLocations, lines);
    }

    /**
     * Deseneaza toate punctele si toate liniile stocate (retained mode)
     */
    private void drawAll() {
        for(Point dot: dotsLocations) {
            drawDot(dot.x, dot.y);
        }
        for(Line line: lines) {
            drawLine(line.start, line.end, line.color);
        }
    }

    // Deseneaza un puncte pe imaginea din memorie
    private void drawDot(int x, int y) {
        offscreenGraphics.setColor(Color.BLACK);
        offscreenGraphics.fillOval(x - dotRadius, y - dotRadius, 2*dotRadius, 2*dotRadius);
    }

    // Deseneaza o linie intre cele doua puncte din memorie
    private void drawLine(Point currentDot, Point clickedPoint, Color color) {
        offscreenGraphics.setColor(color); // Culoarea
        offscreenGraphics.setStroke(new BasicStroke(4)); // Cat de groasa este linia dintre puncte
        offscreenGraphics.drawLine(currentDot.x, currentDot.y, clickedPoint.x, clickedPoint.y);

        //Calculam logica bazata pe linie
        int currentPlayerColor = (color == Color.RED) ? 0 : 1;
        gameLogic.addLine(currentDot, clickedPoint, currentPlayerColor);

        frame.getScoreboard().updateRedScore(gameLogic.getRedScore());
        frame.getScoreboard().updateBlueScore(gameLogic.getBlueScore());
    }

    public void resetGame() {
        gameLogic.reset();
        frame.getScoreboard().updateRedScore(gameLogic.getRedScore());
        frame.getScoreboard().updateBlueScore(gameLogic.getBlueScore());
    }

    // export a desenului curent intr-un fisier PNG
    public boolean exportToPNG(File outputFile) throws IOException {

        repaint();  // redesenam panoul

        // scriem BufferedImage in fisierul PNG
        return ImageIO.write(offscreen, "PNG", outputFile);
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public List<Point> getDotsLocations() {
        return dotsLocations;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
        drawAll(); // redeseneaza tot pe buffer
        repaint();
    }


    public List<Line> getLines() {
        return lines;
    }



    //Afisam imaginea din memorie pe ecran
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(offscreen, 0, 0, this);
    }


}
