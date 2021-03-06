package org.dbflute.erflute.editor.controller.editpart.element.node;

import org.dbflute.erflute.editor.controller.editpart.element.ERDiagramEditPart;
import org.dbflute.erflute.editor.controller.editpolicy.element.node.DiagramWalkerComponentEditPolicy;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.Location;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.category.Category;
import org.dbflute.erflute.editor.view.figure.CategoryFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

public class CategoryEditPart extends DiagramWalkerEditPart implements IResizable {

    public CategoryEditPart() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IFigure createFigure() {
        Category category = (Category) this.getModel();
        CategoryFigure figure = new CategoryFigure(category.getName());

        return figure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Rectangle getRectangle() {
        Rectangle rectangle = super.getRectangle();

        Category category = (Category) this.getModel();
        ERDiagramEditPart rootEditPart = (ERDiagramEditPart) this.getRoot().getContents();

        for (Object child : rootEditPart.getChildren()) {
            if (child instanceof DiagramWalkerEditPart) {
                DiagramWalkerEditPart editPart = (DiagramWalkerEditPart) child;

                if (category.contains((DiagramWalker) editPart.getModel())) {
                    Rectangle bounds = editPart.getFigure().getBounds();

                    if (bounds.x + bounds.width > rectangle.x + rectangle.width) {
                        rectangle.width = bounds.x + bounds.width - rectangle.x;
                    }
                    if (bounds.y + bounds.height > rectangle.y + rectangle.height) {
                        rectangle.height = bounds.y + bounds.height - rectangle.y;
                    }

                    if (rectangle.width != category.getWidth() || rectangle.height != category.getHeight()) {
                        category.setLocation(new Location(category.getX(), category.getY(), rectangle.width, rectangle.height));
                    }
                }
            }
        }

        return rectangle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createEditPolicies() {
        this.installEditPolicy(EditPolicy.COMPONENT_ROLE, new DiagramWalkerComponentEditPolicy());

        super.createEditPolicies();
    }

    @Override
    protected void performRequestOpen() {
    }
}
