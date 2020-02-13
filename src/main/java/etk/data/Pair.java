/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.data;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Pair<L, R> implements Serializable {
    private static final long serialVersionUID = 1L;

    private L left;
    private R right;

    public Pair() {
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public boolean hasRight() {
        return this.right != null;
    }

    public boolean hasLeft() {
        return this.left != null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.left);
        hash = 17 * hash + Objects.hashCode(this.right);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Pair<L, R> other = (Pair<L, R>) obj;

        if (!Objects.equals(this.left, other.left)) {
            return false;
        }

        return Objects.equals(this.right, other.right);
    }

    @Override
    public String toString() {
        return "[" + left + "][" + right + "]";
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<L, R>(left, right);
    }
}
