//
// Generated by JTB 1.1.2
//

package jtb.cparser.syntaxtree;

// Grammar production:
// f0 -> Initializer()
// f1 -> ( "," Initializer() )*
public class InitializerList implements Node {
  static final long serialVersionUID = 20050923L;

   public Initializer f0;
   public NodeListOptional f1;

   public InitializerList(Initializer n0, NodeListOptional n1) {
      f0 = n0;
      f1 = n1;
   }

   public void accept(jtb.cparser.visitor.Visitor v) {
      v.visit(this);
   }
}
