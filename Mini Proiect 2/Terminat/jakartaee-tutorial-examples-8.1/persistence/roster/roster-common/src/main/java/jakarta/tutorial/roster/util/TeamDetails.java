/*
 * Copyright (c) 2014, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.roster.util;

import java.io.Serializable;

public class TeamDetails implements Serializable {

    private static final long serialVersionUID = -1618941013515364318L;
    private String id;
    private String name;
    private String city;
    private int score;
    private int matchesPlayed;

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public TeamDetails(String id, String name, String city, int score, int matchesPlayed) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.score = score;
        this.matchesPlayed = matchesPlayed;
    }

    public TeamDetails(String id, String name, String city) {

        this.id = id;
        this.name = name;
        this.city = city;
    }

    public TeamDetails(String id, String name, String city, int score) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "TEAM ID = " + id + " NAME = " + name + " MATCHES = " + matchesPlayed + " SCORE = " + score;
    }

}
