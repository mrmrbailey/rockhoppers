/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.ebook;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mxbailey
 */
@Entity
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookPK.bookId = :bookId"),
    @NamedQuery(name = "Book.findByAuthorId", query = "SELECT b FROM Book b WHERE b.bookPK.authorId = :authorId"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title")})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookPK bookPK;
    @Column(name = "title", length = 255)
    private String title;
    @ManyToMany(mappedBy = "bookList", fetch = FetchType.LAZY)
    private List<Tag> tagList;
    @JoinColumn(name = "authorId", referencedColumnName = "authorId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Author author;

    public Book() {
    }

    public Book(BookPK bookPK) {
        this.bookPK = bookPK;
    }

    public Book(int bookId, int authorId) {
        this.bookPK = new BookPK(bookId, authorId);
    }

    public BookPK getBookPK() {
        return bookPK;
    }

    public void setBookPK(BookPK bookPK) {
        this.bookPK = bookPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookPK != null ? bookPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookPK == null && other.bookPK != null) || (this.bookPK != null && !this.bookPK.equals(other.bookPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.co.rockhoppersuk.ebook.Book[ bookPK=" + bookPK + " ]";
    }
    
}
