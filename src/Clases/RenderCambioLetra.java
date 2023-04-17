/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Felipe M
 */
public class RenderCambioLetra  extends DefaultTableCellRenderer {
Color color=null;
    public RenderCambioLetra(Color ligColo)
    {
        color=ligColo;
    }
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        Component cell= super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
//            if(o instanceof  Integer)
//            {
//                Integer valor=(Integer) o;
//                if(valor>0)
//                {
//                    cell.setBackground(Color.GREEN);
//                }
//                else
        java.awt.Font f=new java.awt.Font("ARIAL", Font.BOLD, 18 );
        cell.setFont(f);
                    cell.setBackground(color);
//            }
        return cell;
    }
    
    
}

