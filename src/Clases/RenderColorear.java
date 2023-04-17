package Clases;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderColorear extends DefaultTableCellRenderer {

    Color color = null;

    public RenderColorear(Color ligColo) {
        color = ligColo;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        Component cell = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
//            if(o instanceof  Integer)
//            {
//                Integer valor=(Integer) o;
//                if(valor>0)
//                {
//                    cell.setBackground(Color.GREEN);
//                }
//                else
        cell.setBackground(color);
//            }
        return cell;
    }

}
