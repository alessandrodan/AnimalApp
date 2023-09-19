package it.uniba.dib.sms222334.Presenters;

import java.util.List;
import java.util.Map;

import it.uniba.dib.sms222334.Database.Dao.RelationDao;
import it.uniba.dib.sms222334.Models.Animal;
import it.uniba.dib.sms222334.Models.Relation;

public class RelationPresenter {

    public RelationPresenter(){

    }
    private Relation relation;


    public Relation createRelation(String id, Relation.relationType relationCategory, Animal animal) {
        if (animal != null  && isAlphaNumeric(id) && relationCategory != null) {
            relation = Relation.Builder.create(id, relationCategory, animal).build();
            if (relation.createRelation(id,animal.getFirebaseID())) {
                return relation;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public boolean deleteRelation(String idAnimal1,String idAnimal2){
        if (isAlphaNumeric(idAnimal1)) {
            return Relation.deleteRelation(idAnimal1,idAnimal2);
        }else{
            return false;
        }
    }

    public void action_getAnimal(String ownerID,final RelationDao.OnRelationListener listener){
        System.out.println("entrato nel present");
        Relation.getListAnimal(ownerID, new RelationDao.OnRelationListener() {
            @Override
            public void onRelationListener(List<Animal> animalList) {
                listener.onRelationListener(animalList);
            }
        });
    }
    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

}
