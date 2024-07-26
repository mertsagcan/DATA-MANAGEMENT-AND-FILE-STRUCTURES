import java.util.ArrayList;
import java.util.Stack;

public class PlaylistTree {
	
	public PlaylistNode primaryRoot;		//root of the primary B+ tree
	public PlaylistNode secondaryRoot;	//root of the secondary B+ tree
	public PlaylistTree(Integer order) {
		PlaylistNode.order = order;
		primaryRoot = new PlaylistNodePrimaryLeaf(null);
		primaryRoot.level = 0;
		secondaryRoot = new PlaylistNodeSecondaryLeaf(null);
		secondaryRoot.level = 0;
	}
	
	public void addSong(CengSong song) {
		// TODO: Implement this method
		// add methods to fill both primary and secondary tree

		//ADDING PRIMARY
		PlaylistNodePrimaryLeaf leafToInsert = findIndexPrimary(song.audioId());
		ArrayList<CengSong> songsOfLeaf = leafToInsert.getSongs();
		Integer indexToInsert = -1;

		for(int i = 0; i < leafToInsert.songCount(); i++){
			if(songsOfLeaf.get(i).audioId() > song.audioId()){
				indexToInsert = i;
				break;
			}
		}
		if(indexToInsert == -1){
			indexToInsert = leafToInsert.songCount();
		}

		leafToInsert.addSong(indexToInsert, song);

		if(leafToInsert.getParent() == null){
			if(leafToInsert.songCount() == (2 * PlaylistNode.order) + 1){
				PlaylistNodePrimaryIndex newRoot = new PlaylistNodePrimaryIndex(null);
				PlaylistNodePrimaryLeaf newNode = new PlaylistNodePrimaryLeaf(newRoot);

				for(int index = PlaylistNode.order, j = 0; index < leafToInsert.songCount(); index++, j++){
					newNode.addSong(j, leafToInsert.songAtIndex(index));
				}
				leafToInsert.getSongs().subList(PlaylistNode.order, leafToInsert.getSongs().size()).clear();
				leafToInsert.setParent(newRoot);
				newRoot.addAudioId(0, newNode.songAtIndex(0).audioId());
				newRoot.addChild(0, leafToInsert);
				newRoot.addChild(1, newNode);
				primaryRoot = newRoot;
			}
		}

		else {

			if(leafToInsert.songCount() == (2 * PlaylistNode.order) + 1){
				PlaylistNode parent = leafToInsert.getParent();
				PlaylistNodePrimaryLeaf newNode = new PlaylistNodePrimaryLeaf(parent);

				for(int index = PlaylistNode.order, j = 0; index < leafToInsert.songCount(); index++, j++){
					newNode.addSong(j, leafToInsert.songAtIndex(index));
				}
				leafToInsert.getSongs().subList(PlaylistNode.order, leafToInsert.getSongs().size()).clear();
				Integer keyToUp= newNode.audioIdAtIndex(0);
				boolean isSet = false;

				for(int i = 0; i < ((PlaylistNodePrimaryIndex)parent).audioIdCount(); i++){
					if (((PlaylistNodePrimaryIndex)parent).audioIdAtIndex(i) > keyToUp)
					{
						((PlaylistNodePrimaryIndex)parent).addAudioId(i, keyToUp);

						((PlaylistNodePrimaryIndex)parent).addChild(i + 1, newNode);
						isSet = true;
						break;
					}
				}
				if(!isSet){
					((PlaylistNodePrimaryIndex)parent).addAudioId(keyToUp);

					((PlaylistNodePrimaryIndex)parent).addChild(newNode);
				}

				PlaylistNode nextParent;

				while (((PlaylistNodePrimaryIndex)parent).audioIdCount() == (2 * PlaylistNode.order) + 1)
				{
					isSet = false;


					nextParent = parent.getParent();


					if (nextParent == null)
					{
						PlaylistNodePrimaryIndex newRoot = new PlaylistNodePrimaryIndex(null);

						PlaylistNodePrimaryIndex newInternalNode = new PlaylistNodePrimaryIndex(newRoot);

						for (int index = PlaylistNode.order + 1, j = 0 ; index < ((PlaylistNodePrimaryIndex)parent).audioIdCount() ; index++,j++)
						{

							newInternalNode.addAudioId(j,((PlaylistNodePrimaryIndex)parent).audioIdAtIndex(index));
							newInternalNode.addChild(j,((PlaylistNodePrimaryIndex)parent).getChildrenAt(index));
							((PlaylistNodePrimaryIndex)parent).getChildrenAt(index).setParent(newInternalNode);
						}
						newInternalNode.addChild(newInternalNode.audioIdCount(),((PlaylistNodePrimaryIndex)parent).getChildrenAt(((PlaylistNodePrimaryIndex)parent).audioIdCount()));
						((PlaylistNodePrimaryIndex)parent).getChildrenAt(((PlaylistNodePrimaryIndex)parent).audioIdCount()).setParent(newInternalNode);

						keyToUp = ((PlaylistNodePrimaryIndex)parent).audioIdAtIndex(PlaylistNode.order);


						((PlaylistNodePrimaryIndex)parent).getAllAudioIds().subList(PlaylistNode.order, ((PlaylistNodePrimaryIndex)parent).getAllAudioIds().size()).clear();
						((PlaylistNodePrimaryIndex)parent).getAllChildren().subList(PlaylistNode.order + 1, ((PlaylistNodePrimaryIndex)parent).getAllChildren().size()).clear();


						newRoot.addAudioId(0, keyToUp);
						newRoot.addChild(0, parent);
						parent.setParent(newRoot);
						newRoot.addChild(1, newInternalNode);
						primaryRoot = newRoot;
						break;
					}


					else
					{

						PlaylistNodePrimaryIndex newInternalNode = new PlaylistNodePrimaryIndex(nextParent);

						for (int index = PlaylistNode.order + 1, j = 0 ; index < ((PlaylistNodePrimaryIndex)parent).audioIdCount() ; index++,j++)
						{

							newInternalNode.addAudioId(j,((PlaylistNodePrimaryIndex)parent).audioIdAtIndex(index));
							newInternalNode.addChild(j,((PlaylistNodePrimaryIndex)parent).getChildrenAt(index));
							((PlaylistNodePrimaryIndex)parent).getChildrenAt(index).setParent(newInternalNode);
						}
						newInternalNode.addChild(newInternalNode.audioIdCount(),((PlaylistNodePrimaryIndex)parent).getChildrenAt(((PlaylistNodePrimaryIndex)parent).audioIdCount()));
						((PlaylistNodePrimaryIndex)parent).getChildrenAt(((PlaylistNodePrimaryIndex)parent).audioIdCount()).setParent(newInternalNode);

						keyToUp = ((PlaylistNodePrimaryIndex)parent).audioIdAtIndex(PlaylistNode.order);


						((PlaylistNodePrimaryIndex)parent).getAllAudioIds().subList(PlaylistNode.order, ((PlaylistNodePrimaryIndex)parent).getAllAudioIds().size()).clear();
						((PlaylistNodePrimaryIndex)parent).getAllChildren().subList(PlaylistNode.order + 1, ((PlaylistNodePrimaryIndex)parent).getAllChildren().size()).clear();


						for (int i = 0; i < ((PlaylistNodePrimaryIndex)nextParent).audioIdCount(); i++) {
							if (((PlaylistNodePrimaryIndex)nextParent).audioIdAtIndex(i) > keyToUp)
							{
								((PlaylistNodePrimaryIndex)nextParent).addAudioId(i, keyToUp);
								((PlaylistNodePrimaryIndex)nextParent).addChild(i, parent);

								((PlaylistNodePrimaryIndex)nextParent).setChild(i + 1, newInternalNode);
								isSet = true;
								break;
							}
						}
						if (!isSet)
						{

							((PlaylistNodePrimaryIndex)nextParent).addAudioId(keyToUp);

							((PlaylistNodePrimaryIndex)nextParent).addChild(newInternalNode);
						}
						parent = nextParent;
					}
				}
			}
		}
		//ADDING SECONDARY















		return;
	}
	
	public CengSong searchSong(Integer audioId) {
		// TODO: Implement this method
		// find the song with the searched audioId in primary B+ tree
		// return value will not be tested, just print according to the specifications
		PlaylistNode searchNode = primaryRoot;
		String tabCount = "";
		while(searchNode.type != PlaylistNodeType.Leaf){
			ArrayList<Integer> audioIds = ((PlaylistNodePrimaryIndex) searchNode).getAllAudioIds();
			int IdSize = audioIds.size();
			for(int sizeFinder = 0; sizeFinder < audioIds.size(); sizeFinder++){
				if( audioIds.get(sizeFinder) > audioId){
					IdSize = sizeFinder;
					break;
				}
			}

			System.out.println(tabCount + "<index>");
			for(int a: audioIds){
				System.out.println(tabCount + a);
			}
			System.out.println(tabCount + "</index>");
			PlaylistNode child = ((PlaylistNodePrimaryIndex) searchNode).getChildrenAt(IdSize);
			searchNode = child;
			tabCount += "\t";

		}
		ArrayList<CengSong> songs =((PlaylistNodePrimaryLeaf) searchNode).getSongs();
		CengSong holder = null;
		for(CengSong song: songs){
			if(song.audioId() == audioId){
				holder = song;
				break;
			}
		}

		if(holder == null){
			System.out.println("Could not find " + audioId + "");
		}
		else{
			System.out.println(tabCount + "<data>");
			System.out.println(tabCount + "<record>" + holder.audioId() + "|" + holder.genre() + "|" + holder.songName() + "|" + holder.artist() + "</record>");
			System.out.println(tabCount + "</data>");
		}


		// add methods to find the book with the searched key in primary B+ tree
		// return value will not be tested, just print as the specicifications say
		return null;
	}


	public void printPrimaryPlaylist() {
		// TODO: Implement this method
		// print the primary B+ tree in Depth-first order

		Stack<PlaylistNode> playListStack = new Stack<PlaylistNode>();
		PlaylistNode primaryTreeRoot = primaryRoot;

		playListStack.add(primaryTreeRoot);



		while(!playListStack.isEmpty()){
			String levelCheck = "";
			PlaylistNode node = playListStack.pop();

			if(node.getType() == PlaylistNodeType.Internal){

				ArrayList<PlaylistNode> children = ((PlaylistNodePrimaryIndex) node).getAllChildren();

				for(int i = children.size() - 1; i >= 0; i--){
					playListStack.add(children.get(i));
				}

				for(int f = 0; f < ((PlaylistNodePrimaryIndex) node).level - 1; f++){
					levelCheck += "\t";
				}

				System.out.println(levelCheck + "<index>");

				for(int i = 0; i < ((PlaylistNodePrimaryIndex) node).audioIdCount(); i++){
					System.out.println(levelCheck + ((PlaylistNodePrimaryIndex) node).audioIdAtIndex(i));
				}
				System.out.println(levelCheck + "</index>");
			}

			else if(node.getType() == PlaylistNodeType.Leaf){
				for(int f = 0; f < ((PlaylistNodePrimaryLeaf) node).level - 1; f++){
					levelCheck += "\t";
				}

				System.out.println(levelCheck + "<data>");

				for(int i = 0; i < ((PlaylistNodePrimaryLeaf) node).songCount(); i++){
					for(int x = 0; x < node.level; x++){
					}
					System.out.print(levelCheck + "<record>");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).audioId());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).genre());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).songName());
					System.out.print("|");
					System.out.print(((PlaylistNodePrimaryLeaf) node).songAtIndex(i).artist());
					System.out.print("<record>\n");
				}

				System.out.println(levelCheck + "</data>");
			}
			levelCheck += "\t";
		}
	}
	
	public void printSecondaryPlaylist() {
		// TODO: Implement this method
		// print the secondary B+ tree in Depth-first order

		return;
	}
	
	// Extra functions if needed
	public PlaylistNodePrimaryLeaf findIndexPrimary(Integer audioId){

		PlaylistNode node = primaryRoot;
		boolean cont;

		while(node.getType() == PlaylistNodeType.Internal){
			cont = false;
			for(int i = 0; i < ((PlaylistNodePrimaryIndex) node).audioIdCount(); i++){

				if (((PlaylistNodePrimaryIndex) node).audioIdAtIndex(i) > audioId)
				{
					node = ((PlaylistNodePrimaryIndex) node).getChildrenAt(i);
					cont = true;
					break;
				}
			}
			if (!cont)
				node = ((PlaylistNodePrimaryIndex) node).getChildrenAt(((PlaylistNodePrimaryIndex) node).audioIdCount());
		}
		return ((PlaylistNodePrimaryLeaf) node);
	}


	public PlaylistNodeSecondaryLeaf findIndexSecondary(String genre, Integer audioId) {

		PlaylistNode node = secondaryRoot;
		boolean cont;

		while (node.getType() == PlaylistNodeType.Internal)
		{
			cont = false;
			for (int i = 0; i < ((PlaylistNodeSecondaryIndex) node).audioIdCount() ; i++) {

				if (((PlaylistNodeSecondaryIndex) node).genreAtIndex(i) == genre)
				{
					if (((PlaylistNodeSecondaryIndex) node).audioIdAtIndex(i) >= audioId)
					{
						node = ((PlaylistNodeSecondaryIndex) node).getChildrenAt(i);
						cont = true;
						break;
					}
					else
					if (i != 0)
					{
						node = ((PlaylistNodeSecondaryIndex) node).getChildrenAt(i - 1);
						cont = true;
						break;
					}
					else
					{
						node = ((PlaylistNodeSecondaryIndex) node).getChildrenAt(i);
						cont = true;
						break;
					}
				}
				else if (((PlaylistNodeSecondaryIndex) node).genreAtIndex(i).compareTo(genre) > 0)
				{
					node = ((PlaylistNodeSecondaryIndex) node).getChildrenAt(i);
					cont = true;
					break;
				}
			}

			if (!cont)
				node = ((PlaylistNodeSecondaryIndex) node).getChildrenAt(((PlaylistNodeSecondaryIndex) node).genreCount());
		}

		return ((PlaylistNodeSecondaryLeaf) node);
	}
}
























