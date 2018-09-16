package UNIPruebas;

import com.demo.interpol.CubicSpline;
import org.junit.Test;
import java.util.Vector;
import static org.junit.Assert.assertEquals;

public class CubicSplineTest {
    @Test
    public void calculateResultTest(){
        double elresulEsperado=1.600000023841858;
        Vector<Double> pri = new Vector<Double>();
        double obtenido=0.0;
        Vector<Double> seg = new Vector<>();
        double tolerancia=0.0;
        pri.addElement(2.3);
        pri.addElement(3.3);
        pri.addElement(4.3);
        pri.addElement(5.3);
        pri.addElement(6.3);
        seg.addElement(1.5);
        seg.addElement(2.5);
        seg.addElement(3.5);
        seg.addElement(4.5);
        seg.addElement(5.5);
        obtenido= new CubicSpline().calculateResult(2.4, pri, seg);
        assertEquals(elresulEsperado,obtenido,tolerancia);
    }
}