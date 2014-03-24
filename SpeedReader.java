import java.awt.Font;
public class SpeedReader{
    private double wpm; // caps out at around 25250 wpm on my computer
    private Deque<String> a;
    private Deque<String> b;
    private boolean ready;
    private final int DEFAULT_SPEED = 500;
    private int counter;
    private Stopwatch timer;
    
        
    public SpeedReader(double wpm){
        this.wpm = wpm;
        a = new Deque();
        b = new Deque();
        ready = true;
        counter = 0;
        timer = new Stopwatch();
    }
    public String nextWord(){
        String temp;
        
        if(!b.isEmpty())
            temp = b.removeFirst();
        else if (!StdIn.isEmpty()){
            temp = StdIn.readString();
            if(StdIn.isEmpty())
                ready = false;
        }
        else temp = a.removeFirst();
        
        a.addLast(temp);
        return temp;
    }
    public void runNow(){
        StdDraw.setCanvasSize(400, 80*5);
        StdDraw.clear();
        do{
            if (StdDraw.mousePressed()) ready = false;
            if(StdDraw.hasNextKeyTyped())
                playBack(); 
            if(!ready){ 
                waitPLS();
            }
            String temp = nextWord();
            draw(temp);
            if(wpm == 0) ready = false;
            else StdDraw.show((int) (1000.0*60*(temp.length()+5) / (10.0*wpm)));
        }
        while(!StdIn.isEmpty() || !a.isEmpty());
    }
    public void playBack(){
        char STOP = '5';
        char BAC = '0';
        char BACK = '4';
        char BACK2 = '1';
        char FOR = '2';
        char FORWARD = '6';
        char FORWARD2 = '3';
        char SLOW = '7';
        char RESET = '8';
        char FAST = '9';
        char temp = StdDraw.nextKeyTyped();
                if(temp == STOP){
                    ready = !ready;
                }
                else if(temp == BAC){
                    for(int i = 0; i < 2 && !a.isEmpty(); i++){
                        counter--;
                        b.addFirst(a.removeLast());
                    }
                    draw(nextWord());
                }
                else if(temp == BACK){
                    for(int i = 0; i < wpm / 60 + 2 && !a.isEmpty(); i++){
                        counter--;
                        b.addFirst(a.removeLast());
                    }
                    draw(nextWord());
                }
                else if(temp == BACK2){
                    for(int i = 0; i < wpm / 6 + 2 && !a.isEmpty(); i++){
                        counter--;
                        b.addFirst(a.removeLast());
                    }
                    draw(nextWord());
                }
                else if(temp == FOR){
                        draw(nextWord());
                    
                }
                else if(temp == FORWARD){
                    if(!b.isEmpty()){
                        for(int i = 0; i < wpm / 60 && !b.isEmpty(); i++){
                            counter++;
                            a.addLast(b.removeFirst());
                        }
                        draw(nextWord());
                    }
                    else{
                        for(int i = 0; i < wpm / 60 && !StdIn.isEmpty(); i++){
                            counter++;
                            a.addLast(StdIn.readString());
                        }
                        draw(nextWord());
                    }
                }
                else if(temp == FORWARD2){
                    if(!b.isEmpty()){
                        for(int i = 0; i < wpm / 6 && !b.isEmpty(); i++){
                            counter++;
                            a.addLast(b.removeFirst());
                        }
                        draw(nextWord());
                    }
                    else{
                        for(int i = 0; i < wpm / 6 && !StdIn.isEmpty(); i++){
                            counter++;
                            a.addLast(StdIn.readString());
                        }
                        draw(nextWord());
                    }
                }
                else if(temp == SLOW){
                    wpm -= 50;
                }
                else if(temp == RESET){
                    wpm = DEFAULT_SPEED;
                }
                else if(temp == FAST){
                    wpm += 50;
                }
                else StdOut.println(temp);
    }
    public void waitPLS(){
        StdDraw.show(100);
        while(!ready){
            if(StdDraw.mousePressed()) 
                ready = true;
            if(StdDraw.hasNextKeyTyped()){
                playBack();
            }
            StdDraw.show(100);
        }
        ready = true;
    }
	public void draw(String word) {
        double HEIGHT = 5.0;
        double LENGTH = 5.0;
        double N = 0.25;
        //double mid = (double) N*word.length()/2;
        double mid = LENGTH / 2;
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, LENGTH);
        //StdDraw.setYscale(0, HEIGHT);
        StdDraw.setYscale(-4*HEIGHT, HEIGHT);



        // write status text
        StdDraw.line(mid, HEIGHT, mid, 3*HEIGHT / 4);
        StdDraw.line(mid, 0, mid, HEIGHT / 4);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 32));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.redText(mid, HEIGHT / 2, word);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 8));
        StdDraw.text(6.0*mid/4, 0, "Words: " + counter + "    WPM: " + wpm + "   " + timer.toTime()
            + "  Avg: " + (int)(60 * counter / timer.elapsedTime()));
        //StdOut.println(word + " " + N*word.length() + " " + N*word.length()/2);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.paragraph(0, -1, LENGTH, -4*HEIGHT, a);
        counter++;
    }
	public static void main(String[] args){
        int wpm = 500;
        if(args.length > 0)
            wpm = Integer.parseInt(args[0]);
        SpeedReader test = new SpeedReader(wpm);
        test.runNow();
	}
}