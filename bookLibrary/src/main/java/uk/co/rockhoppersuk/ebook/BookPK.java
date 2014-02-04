/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.ebook;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mxbailey
 */
@Embeddable
public class BookPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "bookId", nullable = false)
    private int bookId;
    @Basic(optional = false)
    @Column(name = "authorId", nullable = false)
    private int authorId;

    public BookPK() {
    }

    public BookPK(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) bookId;
        hash += (int) authorId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookPK)) {
            return false;
        }
        BookPK other = (BookPK) object;
        if (this.bookId != other.bookId) {
            return false;
        }
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.co.rockhoppersuk.ebook.BookPK[ bookId=" + bookId + ", authorId=" + authorId + " ]";
    }
    
}
