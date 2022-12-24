package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.AlbumForm;
import jpabook.jpashop.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("Album")
public class Album extends Item{

    private String artist;
    private String etc; //기타정보

    public static Album addAlbum(AlbumForm albumForm) {
        Album album = new Album();
        album.setName(albumForm.getName());
        album.setPrice(albumForm.getPrice());
        album.setStockQuantity(albumForm.getStockQuantity());
        album.setArtist(albumForm.getArtist());
        album.setEtc(album.getEtc());
        return album;
    }
}
