package si.isaap.swipesnake;

/**
 * Created by domen_000 on 28. 03. 2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Majstr on 7. 03. 2016.
 */
public class Snake extends View{
    ArrayList<Integer> kacaX = new ArrayList<Integer>();
    ArrayList<Integer> kacaY = new ArrayList<Integer>();
    int[] okvirX = new int[4];
    int[] okvirY = new int[4];
    public static int tocke = 0;

    private int[] food = new int[]{120, 120};

    boolean prvic = true;

    Paint paint = new Paint();
    Paint obrobaPaint = new Paint();
    int smer = 3;
    int staraSmer = 3;
    static int debelina = 40;
    static int debelinaObroba = 1;
    private boolean konec = false;
//    static int razmik = 3;

    public Snake(Context context) {

        super(context);



        okvirX[0]=0;
        okvirY[0]=0;
        okvirY[1]=0;
        okvirX[3]=0;

        obrobaPaint.setStrokeWidth(debelinaObroba*2);
        obrobaPaint.setStyle(Paint.Style.STROKE);
        obrobaPaint.setColor(Color.BLACK);

    }

    @Override
    public void onDraw(Canvas canvas) {
        int tempSmer;
        if(prvic){
            prvic = false;
            int thisWidth = this.getWidth();
            int thisHeight = this.getHeight();
            int widthPrevec = thisWidth%debelina;
            int heightPrevec = thisHeight%debelina;
            int width = 0;
            int height = 0;

            if(debelina/2<widthPrevec){
                width = thisWidth+(debelina-widthPrevec);
            }else{
                width = thisWidth-(widthPrevec);
            }
            if(debelina/2<heightPrevec) {
                height = thisHeight - (heightPrevec);
            }else{
                height = thisHeight + (debelina-heightPrevec);
            }
            okvirX[1]=width;
            okvirX[2]=width;
            okvirY[2]=height;
            okvirY[3]=height;

            int tempX = thisWidth/2-((thisWidth/2)%debelina);
            int tempY = thisHeight/2-((thisHeight/2)%debelina);

            kacaX.add(tempX);
            kacaX.add(tempX-3*debelina);
            kacaY.add(tempY);
            kacaY.add(tempY);
        }
//        !preveriKaco()
        prestavi();
        if(!preveriKaco()) {
            Log.w("IGRA", "NE RIŠE VEČ");
            konec = true;
        }else{
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(debelina*2);

            for(int i = 0; i < 4; i++){
                canvas.drawLine(okvirX[(i + 3) % 4], okvirY[(i + 3) % 4], okvirX[(i + 1 + 3) % 4], okvirY[(i +1+3)%4], paint);
            }

            int x_start, y_start;
            int x, y, k;
            int st_kvadratov;
            int a;
            Rect r = null;
            Rect o = null;

            paint.setColor(Color.parseColor("#FF4081"));
            canvas.drawRect(food[0], food[1], food[0] + debelina, food[1] + debelina, paint);

            paint.setColor(Color.WHITE);

            for(int i = 0; i < kacaX.size()-1; i++){
                x_start = kacaX.get(i);
                y_start = kacaY.get(i);
                x = kacaX.get(i) - kacaX.get(i+1);
                y = kacaY.get(i) - kacaY.get(i+1);
                //smer
                if(x==0){
                    if(y<0){
                        //dol 0
                        tempSmer = 0;
                    }else{
                        //gor 1
                        tempSmer = 1;
                    }
                }else{
                    if(x<0){
                        //desno 2
                        tempSmer = 2;
                    }else{
                        //levo 3
                        tempSmer = 3;
                    }
                }

                y = Math.abs(y);
                x = Math.abs(x);
                k = (x==0)?y:x;
                st_kvadratov  = k/debelina;

                for(a = 0; a < st_kvadratov; a++){
                    switch (tempSmer) {
                        case 0:
                            r = new Rect(x_start, y_start + a * debelina, x_start + debelina, y_start + a * debelina + debelina);
                            o = new Rect(x_start+debelinaObroba, y_start + a * debelina+debelinaObroba, x_start + debelina-debelinaObroba, y_start + a * debelina + debelina-debelinaObroba);
                            break;
                        case 1:
                            r = new Rect(x_start, y_start - a * debelina, x_start + debelina, y_start - a * debelina + debelina);
                            o = new Rect(x_start+debelinaObroba, y_start - a * debelina+debelinaObroba, x_start + debelina-debelinaObroba, y_start - a * debelina + debelina-debelinaObroba);

                            break;
                        case 2:
                            r = new Rect(x_start + a * debelina, y_start, x_start + a * debelina + debelina, y_start + debelina);
                            o = new Rect(x_start + a * debelina+debelinaObroba, y_start+debelinaObroba, x_start + a * debelina + debelina-debelinaObroba, y_start + debelina-debelinaObroba);

                            break;
                        case 3:
                            r = new Rect(x_start - a * debelina, y_start, x_start - a * debelina + debelina, y_start + debelina);
                            o = new Rect(x_start - a * debelina+debelinaObroba, y_start+debelinaObroba, x_start - a * debelina + debelina-debelinaObroba, y_start + debelina-debelinaObroba);
                            break;
                    }
                    canvas.drawRect(r, paint);
                    canvas.drawRect(o,obrobaPaint);

                }
            }


        /*paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint);
*/
//            prestavi();
        }
    }

    private boolean preveriKaco() {
        //ali je znotraj kvadrata?
        int i = 0;
        if(kacaY.get(0)>=okvirY[3]-debelina||kacaY.get(0)<okvirY[0]+debelina||(kacaX.get(0)+debelina)>(okvirX[1]-debelina)||(kacaX.get(0))<(okvirX[0]+debelina)){
            return false;
        }
        if(jeTockaNaKaci(kacaX.get(0), kacaY.get(0), "g")){
            return false;
        }
        return true;

//        for(i = 0; i < 4; i++){
//
//        }

    }

    private void prestavi(){
        int x;
        int y;
        //PRESTAVI SPREDN DEL
        if(staraSmer != smer){
            switch (smer) {
                case 0:
                    kacaX.add(0, kacaX.get(0));
                    kacaY.add(0, kacaY.get(0)+debelina);
                    break;
                case 1:
                    kacaX.add(0, kacaX.get(0)-debelina);
                    kacaY.add(0, kacaY.get(0));
                    break;
                case 2:
                    kacaX.add(0, kacaX.get(0));
                    kacaY.add(0, kacaY.get(0)-debelina);
                    break;
                case 3:
                    kacaX.add(0, kacaX.get(0)+debelina);
                    kacaY.add(0, kacaY.get(0));

                    break;
            }
            staraSmer = smer;
        } else {
            x = kacaX.get(0) - kacaX.get(1);
            y = kacaY.get(0) - kacaY.get(1);
            if (x == 0) {
                if (y < 0) {
                    //dol 0
                    kacaY.set(0, kacaY.get(0) - debelina);
                } else {
                    //gor 1
                    kacaY.set(0, kacaY.get(0) + debelina);
                }
            } else {
                if (x < 0) {
                    //desno 2
                    kacaX.set(0, kacaX.get(0) - debelina);
                } else {
                    //levo 3
                    kacaX.set(0, kacaX.get(0) + debelina);
                }
            }
        }

        //PRESTAVI ZADN DEL
        if(!jeTockaNaKaci(food[0], food[1],"food")){
            int zadn = kacaX.size()-1;
            x = kacaX.get(zadn) - kacaX.get(zadn-1);
            y = kacaY.get(zadn) - kacaY.get(zadn-1);
            //smer
            if(x==0){
                if(y<0){
                    //dol 0
                    kacaY.set(zadn, kacaY.get(zadn) + debelina);
                }else{
                    //gor 1
                    kacaY.set(zadn, kacaY.get(zadn) - debelina);
                }
            }else{
                if (x < 0) {
                    //desno 2
                    kacaX.set(zadn, kacaX.get(zadn) + debelina);
                }else{
                    //levo 3
                    kacaX.set(zadn, kacaX.get(zadn) - debelina);
                }
            }

            if(kacaX.get(zadn).equals(kacaX.get(zadn-1))&&kacaY.get(zadn).equals(kacaY.get(zadn-1))){
                kacaX.remove(zadn);
                kacaY.remove(zadn);
            }
        }else{
            tocke += 10;
            Random rand = new Random();
            while(jeTockaNaKaci(food[0], food[1],"food")) {
                int randomX = rand.nextInt(((okvirX[1] - debelina) + 1) - debelina) + debelina;
                int randomY = rand.nextInt(((okvirY[3] - debelina) + 1) - debelina) + debelina;
                food[0] = randomX - (randomX % debelina);
                food[1] = randomY - (randomY % debelina);
                Log.wtf("food", "food spawned");
            }
        }
    }
    private void spawnFood(){

    }
    private boolean jeTockaNaKaci(int tockaX, int tockaY, String kaj){
        int x, y;
        for(int i = (kaj.equals("food")?0:1); i < kacaX.size()-1; i++){
            x = kacaX.get(i) - kacaX.get(i+1);
            y = kacaY.get(i) - kacaY.get(i+1);
            //smer
            if(x==0){
                if(tockaX==kacaX.get(i)&&((tockaY>=kacaY.get(i)&&tockaY<=kacaY.get(i+1))||(tockaY<=kacaY.get(i)&&tockaY>=kacaY.get(i+1)))){
                    return true;
                }
            }else if(y==0){
                if(tockaY==kacaY.get(i)&&((tockaX>=kacaX.get(i)&&tockaX<=kacaX.get(i+1))||(tockaX<=kacaX.get(i)&&tockaX>=kacaX.get(i+1)))){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean jeKonec(){
        return this.konec;
    }
    public int getTocke(){
        return this.tocke;
    }
    public void spremeniSmer(int novaSmer){
        if(Math.abs(novaSmer-smer)!=2&&smer==staraSmer){
            this.smer = novaSmer;
        }
    }
}
