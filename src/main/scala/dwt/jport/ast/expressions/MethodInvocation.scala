package dwt.jport.ast.expressions

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ Expression => JdtExpression }
import org.eclipse.jdt.core.dom.{ MethodInvocation => JdtMethodInvocation }
import org.eclipse.jdt.core.dom.Type

import dwt.jport.ast.Invocation
import dwt.jport.ast.expressions.ExpressionImplicits._
import dwt.jport.translators.MethodInvocationTranslator

class MethodInvocation(node: JdtMethodInvocation) extends Expression(node) with Invocation {
  protected def typedArguments =
    node.arguments.asInstanceOf[JavaList[JdtExpression]]

  protected def typedTypeArguments =
    node.typeArguments.asInstanceOf[JavaList[Type]]

  protected def nameExpression = node.getName.toJPort

  val expression = Option(node.getExpression).map(_.toJPort)

  override def translate = MethodInvocationTranslator.translate(this)
}
