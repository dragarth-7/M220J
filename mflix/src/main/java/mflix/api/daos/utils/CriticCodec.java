package mflix.api.daos.utils;

import mflix.api.models.Critic;
import mflix.api.models.Session;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.*;

public class CriticCodec implements CollectibleCodec<Critic> {
    private final Codec<Document> documentCodec;

    public CriticCodec() {
        super();
        this.documentCodec = new DocumentCodec();
    }

    public void encode(
            BsonWriter bsonWriter, Critic critic, EncoderContext encoderContext) {
        Document criticDoc = new Document();
        String criticId = critic.getId();
        Integer numComments = critic.getNumComments();

        if (criticId != null) {
            criticDoc.put("_id", criticId);
        }
        if (numComments != null) {
            criticDoc.put("count", numComments);
        }

        documentCodec.encode(bsonWriter, criticDoc, encoderContext);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Critic decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document criticDoc = documentCodec.decode(bsonReader, decoderContext);
        Critic critic = new Critic();
        critic.setId(criticDoc.getString("_id"));
        critic.setNumComments(criticDoc.getInteger("count"));
        return critic;
    }

    @Override
    public Class<Critic> getEncoderClass() {
        return Critic.class;
    }

    @Override
    public Critic generateIdIfAbsentFromDocument(Critic critic) {
        return critic;
    }

    @Override
    public boolean documentHasId(Critic session) {
        return null != session.getId();
    }

    @Override
    public BsonString getDocumentId(Critic critic) {
        if (!documentHasId(critic)) {
            throw new IllegalStateException("This document does not have an " + "_id");
        }

        return new BsonString(critic.getId());
    }

}
