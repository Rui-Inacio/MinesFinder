
package pt.ipleiria.estg.dei.es.minesfinder.tests;



import pt.ipleiria.estg.dei.es.minesfinder.CampoMinado;


public class CampoMinadoTest {
    
    
    public CampoMinadoTest() {
    }
    
   

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testCriacaoCampoMinadoValido(){
        CampoMinado cm = new CampoMinado(10, 15, 5);
        
        assertEquals( "Largura devolvida invalida", 10, cm.getLargura());
        assertEquals(15, cm.getAltura(), "Altura devolvida invalida");
        assertEquals(5, cm.getNumMinas(), "Numero de minas devolvidas invalido");
        
    }
    
    @Test
    public void testCriacaoCampoMinadoSemMinas() {
        try {
            CampoMinado cm=new CampoMinado(10, 15, 0);
             fail("Conseguiu criar um campo minado sem minas");
            } catch (Exception e) {
                
        }
    }
    
}
