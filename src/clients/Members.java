package clients;

import java.util.ArrayList;

import java.util.Comparator;

public class Members {
    private ArrayList<GymMember> memberList = new ArrayList<>();

    public Members(ArrayList<GymMember> memberList) {
        this.memberList = memberList;
        this.updateMemberships();
    }

    public GymMember getMember(int index) {
        return this.memberList.get(index);
    }

    public ArrayList<GymMember> getMemberList() {
        return memberList;
    }

    public void addMember(GymMember member) {
        memberList.add(member);
        sortBySocial();
    }

    public void updateMemberships() {
        for (GymMember m : this.memberList) {
            m.updateMembership();
        }
    }

    public boolean activeMember(String name) {
        for (GymMember m : this.memberList) {
            if (m.getName().equalsIgnoreCase(name) && m.activeMembership) {
                return true;
            }
        }
        return false;
    }

    public boolean activeMember(Long socialSecurity) {
        for (GymMember m : this.memberList) {
            if (m.getSocialSecurity() == socialSecurity && m.activeMembership) {
                return true;
            }
        }
        return false;
    }

   public int getMemberIndex(String name) {
        for (int i = 0; i < this.memberList.size(); i++) {
            if (this.memberList.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

   public int getMemberIndex(Long socialSecurity) { asdaqsdasd
       for (int i = 0; i < this.memberList.size(); i++) {
           if (this.memberList.get(i).getSocialSecurity() == socialSecurity) {
               return i;
           }
       }
       return -1;
   }

    public boolean memberExist(String name) {
        name = name.trim();
        for (GymMember m : this.memberList) {
            if (name.equalsIgnoreCase(m.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean memberExist(Long socialSecurity) {
        for (GymMember m : this.memberList) {
            if (socialSecurity == m.getSocialSecurity()) {
                return true;
            }
        }
        return false;
    }

    public void sortBySocial() {
        this.memberList.sort(Comparator.comparingLong(m -> m.socialSecurity));
    }
}
